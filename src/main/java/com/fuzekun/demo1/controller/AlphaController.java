package com.fuzekun.demo1.controller;

import com.fuzekun.demo1.entity.community.Comment;
import com.fuzekun.demo1.entity.community.DiscussPost;
import com.fuzekun.demo1.entity.community.Page;
import com.fuzekun.demo1.entity.community.User;
import com.fuzekun.demo1.service.*;
import com.fuzekun.demo1.utils.CommunityConstant;
import com.fuzekun.demo1.utils.CommunityUtil;

import com.fuzekun.demo1.utils.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping("/alpha")
public class AlphaController {


    @Autowired
    private AlphaService alphaService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private CommentService commentService;

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private UserService userService;


    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello() {
        return "Hello Spring Boot.";
    }


    @RequestMapping("data")
    @ResponseBody
    public String getData() {
        return alphaService.find();
    }

    @RequestMapping("/postList/{userId}")
    public String List(@PathVariable("userId") int userId, Model model, Page page, @RequestParam(name = "orderMode", defaultValue = "0") int orderMode) {
        // 获取贴子列表和贴子数目
        page.setRows(discussPostService.findDiscussPostRows(userId));
        String path = "/alpha/postList/" + userId + "?orderMode=" + orderMode;
        page.setPath(path);

        List<DiscussPost> list = discussPostService.findDiscussPosts(userId, page.getOffset(), page.getLimit(), orderMode); // 这里也使用了缓存
        List<Map<String, Object>>discussPosts = new ArrayList<>();
        if (list != null) {
            for (DiscussPost post : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("post", post);

                long likeCount = likeService.findEntityLikeCount(CommunityConstant.ENTITY_TYPE_POST, post.getId());   // 这里从redis中获取
                map.put("likeCount", likeCount);

                discussPosts.add(map);
            }
        }
        model.addAttribute("discussPosts", discussPosts);
        model.addAttribute("orderMode", orderMode);
        model.addAttribute("cnt", discussPostService.findDiscussPostRows(userId));
        model.addAttribute("userId", userId);

        return "site/my-post";
    }

    @RequestMapping("/rely/{userId}")
    public String list(@PathVariable("userId") int userId, Model model, Page page, @RequestParam(name = "orderMode", defaultValue = "0") int orderMode) {
        // 所有贴子评论
        page.setRows(commentService.findUserAllCommentCount(userId));

        // 所有评论评论

        String path = "/alpha/rely/" + userId;
        page.setPath(path);
        int cnt = commentService.findUserAllCommentCount(userId);

        List<Comment> list2 = commentService.findUserAllComment(userId, page.getOffset(), page.getLimit());
        List<Comment> list = new ArrayList<>();

        // 帖子和评论不同对待
        int len = list2.size();
        for (int i = 0; i < len; i++) {
            Comment comment = list2.get(i);
            // 如果是评论的回复找到贴子的id
            if (comment.getEntityType() == CommunityConstant.ENTITY_TYPE_COMMENT) {
                int postId = commentService.findCommentById(comment.getEntityId()).getEntityId();
                comment.setEntityId(postId);
                comment.setPostTitle(discussPostService.findDiscussPostById(postId).getTitle());
            }
            else {
                // 如果是贴子评论，直接评论就行了
                comment.setPostTitle(discussPostService.findDiscussPostById(comment.getEntityId()).getTitle());
            }
            list.add(comment);
        }

        List<Map<String, Object>>comments = new ArrayList<>();
        // 根据不同类型找到点赞数量
        for (Comment comment : list) {
            Map<String, Object>mp = new HashMap<>();
            long likeCnt = likeService.findEntityLikeCount(CommunityConstant.ENTITY_TYPE_COMMENT, comment.getId());
            mp.put("likeCount", likeCnt);
            mp.put("comment", comment);
            comments.add(mp);
        }

        // 封装
        model.addAttribute("comments", comments);
        model.addAttribute("cnt", cnt);
        model.addAttribute("userId", userId);
        model.addAttribute("orderMod", orderMode);

        return "site/my-reply";
    }

    @RequestMapping("/activity")
    public String activity() {
        return "site/acti";
    }

    @RequestMapping("/lottery")
    public String lottery() {
        return "site/lottery";
    }

    @RequestMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response) {
        // 获取请求数据
        System.out.println(request.getMethod());
        System.out.println(request.getServletPath());
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            String value = request.getHeader(name);
            System.out.println(name + ": " + value);
        }
        System.out.println(request.getParameter("code"));

        // 返回响应数据
        response.setContentType("text/html;charset=utf-8");
        try (
                PrintWriter writer = response.getWriter();
        ) {
            writer.write("<h1>牛客网</h1>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // GET请求

    // /students?current=1&limit=20
    @RequestMapping(path = "/students", method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(
            @RequestParam(name = "current", required = false, defaultValue = "1") int current,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit) {
        System.out.println(current);
        System.out.println(limit);
        return "some students";
    }

    // /student/123
    @RequestMapping(path = "/student/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getStudent(@PathVariable("id") int id) {
        System.out.println(id);
        return "a student";
    }

    // POST请求
    @RequestMapping(path = "/student", method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name, int age) {
        System.out.println(name);
        System.out.println(age);
        return "success";
    }

    // 响应HTML数据

    @RequestMapping(path = "/teacher", method = RequestMethod.GET)
    public ModelAndView getTeacher() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("name", "张三");
        mav.addObject("age", 30);
        mav.setViewName("/demo/view");
        return mav;
    }

    @RequestMapping(path = "/school", method = RequestMethod.GET)
    public String getSchool(Model model) {
        model.addAttribute("name", "北京大学");
        model.addAttribute("age", 80);
        return "/demo/view";
    }

    // 响应JSON数据(异步请求)
    // Java对象 -> JSON字符串 -> JS对象

    @RequestMapping(path = "/emp", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getEmp() {
        Map<String, Object> emp = new HashMap<>();
        emp.put("name", "张三");
        emp.put("age", 23);
        emp.put("salary", 8000.00);
        return emp;
    }

    @RequestMapping(path = "/emps", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> getEmps() {
        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> emp = new HashMap<>();
        emp.put("name", "张三");
        emp.put("age", 23);
        emp.put("salary", 8000.00);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name", "李四");
        emp.put("age", 24);
        emp.put("salary", 9000.00);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name", "王五");
        emp.put("age", 25);
        emp.put("salary", 10000.00);
        list.add(emp);

        return list;
    }

    // cookie示例

    @RequestMapping(path = "/cookie/set", method = RequestMethod.GET)
    @ResponseBody
    public String setCookie(HttpServletResponse response) {
        // 创建cookie
        Cookie cookie = new Cookie("code", CommunityUtil.generateUUID());
        // 设置cookie生效的范围
        cookie.setPath("/community/alpha");
        // 设置cookie的生存时间
        cookie.setMaxAge(60 * 10);
        // 发送cookie
        response.addCookie(cookie);

        return "set cookie";
    }

    @RequestMapping(path = "/cookie/get", method = RequestMethod.GET)
    @ResponseBody
    public String getCookie(@CookieValue("code") String code) {
        System.out.println(code);
        return "get cookie";
    }

    // session示例

    @RequestMapping(path = "/session/set", method = RequestMethod.GET)
    @ResponseBody
    public String setSession(HttpSession session) {
        session.setAttribute("id", 1);
        session.setAttribute("name", "Test");
        return "set session";
    }

    @RequestMapping(path = "/session/get", method = RequestMethod.GET)
    @ResponseBody
    public String getSession(HttpSession session) {
        System.out.println(session.getAttribute("id"));
        System.out.println(session.getAttribute("name"));
        return "get session";
    }

}
