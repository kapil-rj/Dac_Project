package controller;

import dao.*;
import entity.*;
import form.*;
import java.util.List;
import model.*;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pagination.PaginationResult;
import validator.ProductFormValidator;

@Controller
@Transactional
public class AdminController {

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private ProductDAO productDAO;
    
    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private ProductFormValidator productFormValidator;

    @InitBinder
    public void myInitBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        if (target.getClass() == ProductForm.class) {
            dataBinder.setValidator(productFormValidator);
        }
    }

    // GET: Show Login Page
    @RequestMapping(value = {"/admin/login"}, method = RequestMethod.GET)
    public String login(Model model) {

        return "login";
    }

    @RequestMapping(value = {"/admin/accountInfo"}, method = RequestMethod.GET)
    public String accountInfo(Model model,
            @RequestParam(value = "name", defaultValue = "") String likeName,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "prodCat", defaultValue = "0") int prodCat,
            @RequestParam(value = "code", required = false) String code) {
        final int maxResult = 5;
        final int maxNavigationPage = 10;

        PaginationResult<ProductInfo> result = (PaginationResult<ProductInfo>) productDAO.queryProducts(page, //
                maxResult, maxNavigationPage, likeName, prodCat, code);

        model.addAttribute("paginationProducts", result);
        return "productList";
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        System.out.println(userDetails.getPassword());
//        System.out.println(userDetails.getUsername());
//        System.out.println(userDetails.isEnabled());
//
//        model.addAttribute("userDetails", userDetails);
//        return "accountInfo";
    }

    @RequestMapping(value = {"/admin/orderList"}, method = RequestMethod.GET)
    public String orderList(Model model, //
            @RequestParam(value = "page", defaultValue = "1") String pageStr) {
        int page = 1;
        try {
            page = Integer.parseInt(pageStr);
        } catch (NumberFormatException e) {
        }
        final int MAX_RESULT = 5;
        final int MAX_NAVIGATION_PAGE = 10;

        PaginationResult<OrderInfo> paginationResult //
                = orderDAO.listOrderInfo(page, MAX_RESULT, MAX_NAVIGATION_PAGE);

        model.addAttribute("paginationResult", paginationResult);
        return "orderList";
    }

    // GET: Show product.
    @RequestMapping(value = {"/admin/product"}, method = RequestMethod.GET)
    public String product(Model model, @RequestParam(value = "code", defaultValue = "") String code) {
        ProductForm productForm = null;

        if (code != null && code.length() > 0) {
            Product product = productDAO.findProduct(code);
            ProductDetails productDetails = productDAO.findProductDetails(code);
            if (product != null && productDetails != null) {
                productForm = new ProductForm(product, productDetails);
            }
        }
        if (productForm == null) {
            productForm = new ProductForm();
            productForm.setNewProduct(true);
        }
        model.addAttribute("productForm", productForm);
        return "product";
    }

    // POST: Save product
    @RequestMapping(value = {"/admin/product"}, method = RequestMethod.POST)
    public String productSave(Model model, //
            @ModelAttribute("productForm") @Validated ProductForm productForm, //
            BindingResult result, //
            final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "product";
        }
        try {
            productDAO.save(productForm);
        } catch (Exception e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            String message = rootCause.getMessage();
            model.addAttribute("errorMessage", message);
            // Show product form.
            return "product";
        }

        return "redirect:/productList";
    }

    // GET: Show User.
    @RequestMapping(value = {"/admin/user"}, method = RequestMethod.GET)
    public String user(Model model, @RequestParam(value = "userName", defaultValue = "") String userName) {
        UserForm userForm = null;

        if (userName != null && userName.length() > 0) {
            Account user = accountDAO.findAccount(userName);
            if (user != null) {
                userForm = new UserForm(user);
            }
        }
        if (userForm == null) {
            userForm = new UserForm();
            userForm.setNewUser(true);
        }
        model.addAttribute("userForm", userForm);
        return "user";
    }

    // POST: Save product
    @RequestMapping(value = {"/admin/user"}, method = RequestMethod.POST)
    public String userSave(Model model, //
            @ModelAttribute("userForm") @Validated UserForm userForm, //
            BindingResult result, //
            final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "user";
        }
        try {
            accountDAO.save(userForm);
        } catch (Exception e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            String message = rootCause.getMessage();
            model.addAttribute("errorMessage", message);
            // Show user form.
            return "user";
        }
        return "redirect:/admin/userList";
    }

    @RequestMapping(value = {"/admin/userDelete"}, method = RequestMethod.GET)
    public String userDelete(@RequestParam String userName) {
        accountDAO.deleteUser(userName);
        return "redirect:/admin/userList";
    }
    
    @RequestMapping(value = {"/admin/userList"}, method = RequestMethod.GET)
    public String userList(Model model, //
            @RequestParam(value = "page", defaultValue = "1") String pageStr) {
        int page = 1;
        try {
            page = Integer.parseInt(pageStr);
        } catch (NumberFormatException e) {
        }
        final int MAX_RESULT = 5;
        final int MAX_NAVIGATION_PAGE = 10;

        PaginationResult<UserInfo> paginationResult //
                = accountDAO.listUserInfo(page, MAX_RESULT, MAX_NAVIGATION_PAGE);

        model.addAttribute("paginationResult", paginationResult);
        return "userList";
    }
    
    @RequestMapping(value = {"/admin/order"}, method = RequestMethod.GET)
    public String orderView(Model model, @RequestParam("orderId") String orderId) {
        OrderInfo orderInfo = null;
        if (orderId != null) {
            orderInfo = this.orderDAO.getOrderInfo(orderId);
        }
        if (orderInfo == null) {
            return "redirect:/admin/orderList";
        }
        List<OrderDetailInfo> details = this.orderDAO.listOrderDetailInfos(orderId);
        orderInfo.setDetails(details);

        model.addAttribute("orderInfo", orderInfo);

        return "order";
    }

    @RequestMapping(value = {"/admin/productDelete"}, method = RequestMethod.GET)
    public String productDelete(@RequestParam String code) {
        productDAO.deleteProduct(code);
        return "redirect:/productList";
    }

}
