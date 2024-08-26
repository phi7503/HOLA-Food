/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.OrderDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Order;
import model.OrderLine;
import model.User;

/**
 *
 * @author ngoba
 */
public class ProfileServlet extends HttpServlet {
 
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {

        }
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("orderDao", OrderDAO.INSTANCE);
        HttpSession ses = request.getSession();
        User u = (User) ses.getAttribute("account");
        if (u != null) {
            int endPage;
            int index = 1;
            String index_raw = request.getParameter("index");
            List<OrderLine> list = OrderDAO.INSTANCE.getOrderLineByUser(u.getId());
            index = (index_raw == null || index_raw.equals("")) ? 1 : Integer.parseInt(index_raw);
            endPage = list.size() / 6;
            if (list.size() % 6 != 0) {
                endPage++;
            }
            List<OrderLine> orders = OrderDAO.INSTANCE.getOrderLineByUserPaging(u.getId(), index);
            request.setAttribute("index", index);
            request.setAttribute("endP", endPage);
            request.setAttribute("listOrder", orders);
        }
        request.getRequestDispatcher("profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
