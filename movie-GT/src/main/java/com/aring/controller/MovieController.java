package com.aring.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.aring.bean.BasicInfo;
import com.aring.bean.MUser;
import com.aring.bean.Movie;
import com.aring.service.MUserService;
import com.aring.service.MovieService;

@Controller
public class MovieController {

	@Autowired
	private MovieService movieService;
	
	@Autowired
	private MUserService muserService;
	
	@RequestMapping("/")
	public ModelAndView index(HttpSession session){
		MUser user = (MUser) session.getAttribute("user");
		String flag = (String) session.getAttribute("loginFlag");
		ModelAndView mav = new ModelAndView("index");
		List<BasicInfo> basicInfos = movieService.getAllBasicInfo();
		mav.addObject("basicInfos", basicInfos);
		if(user!=null){
			mav.addObject("user", user);
			mav.addObject("loginFlag",flag);
		}
		return mav;
	}
	
	@RequestMapping("user")
	public ModelAndView userView(HttpSession session){
		ModelAndView mav = new ModelAndView("user");
		MUser user = (MUser) session.getAttribute("user");
		String flag = (String) session.getAttribute("loginFlag");
		mav.addObject("user", user);
		mav.addObject("loginFlag",flag);
		return mav;
	}
	
	
	@RequestMapping(value="save-user",method=RequestMethod.POST)
	public String saveUser(HttpSession session,String email){
		MUser user = (MUser) session.getAttribute("user");
		user.setEmail(email);
		muserService.updateMUser(user);
		session.setAttribute("user", user);
		return "redirect:/";
	}
	
	
	
	
	
	
	@RequestMapping("/login-out")
	public String loginOut(HttpSession session){
		session.removeAttribute("user");
		session.removeAttribute("loginFlag");
		return "redirect:/";
	}
	
	
	
	
	@RequestMapping("/movie")
	public ModelAndView movie(Integer id,HttpSession session){
		MUser user = (MUser) session.getAttribute("user");
		String flag = (String) session.getAttribute("loginFlag");
		ModelAndView mav = new  ModelAndView("movie");
		Movie movie = movieService.getMovieById(id);
		mav.addObject("movie", movie);
		if(user!=null){
			mav.addObject("user", user);
			mav.addObject("loginFlag",flag);
		}
		return mav;
	}
	
	
	@RequestMapping("/get-movie-number")
	public void getMovieNumber(Integer id,HttpServletResponse resp) throws Exception{
		resp.setCharacterEncoding("utf-8");
		PrintWriter out = resp.getWriter();
		String result = null;
		try {
			Movie movie = movieService.getMovieById(id);
			result = "{success:true,number:"+movie.getNumber()+"}";
		} catch (Exception e) {
			e.printStackTrace();
			result="{success:false,msg:\"查询异常\"}";
		}finally{
			out.write(result);
			out.close();
		}
	}
	
	
	@RequestMapping("/movie-GT")
	public void movieGT(Integer id,HttpSession session,HttpServletResponse resp) throws Exception{
		resp.setCharacterEncoding("utf-8");
		MUser user  = (MUser) session.getAttribute("user");
		PrintWriter out = resp.getWriter();
		String result = null;
		try {
			
			if(user==null){
				result = "{success:false,msg:\"尚未登陆，请登录后再操作\"}";
			}else if(user.getEmail()==null || "".equals(user.getEmail())){
				result = "{success:false,code:1001,msg:\"邮箱没有完善，请填写邮箱地址\"}";
			}else if(id==null || id<1){
				result = "{success:false,msg:\"参数不合法\"}";
			}else{
				movieService.movieGT(id,user);
				result = "{success:true,msg:\"成功抢到影票，请登录邮箱查看\"}";
			}
		} catch (Exception e) {
			result = "{success:false,msg:\""+e.getMessage()+"\"}";
		}finally{
			out.write(result);
			out.close();
		}		
	}
	
	
	
	
	
	
	
	
}
