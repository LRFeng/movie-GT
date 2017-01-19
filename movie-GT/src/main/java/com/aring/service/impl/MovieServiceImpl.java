package com.aring.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aring.bean.BasicInfo;
import com.aring.bean.ImageInfo;
import com.aring.bean.MOrder;
import com.aring.bean.MUser;
import com.aring.bean.Movie;
import com.aring.dao.MUserDao;
import com.aring.dao.MovieDao;
import com.aring.dao.RedisDao;
import com.aring.service.EmailService;
import com.aring.service.MovieService;

@Service
public class MovieServiceImpl implements MovieService{

	@Autowired
	private MovieDao movieDao;
	
	@Autowired
	private RedisDao redisDao;
	
	@Autowired
	private MUserDao mUserDao;
	
	@Autowired
	private EmailService emailService;
	
	@Value(value="http://movie.aringciaran.cn")
	private String domain;
	
	@Override
	public Movie getMovieById(int id) {
		Movie movie =  movieDao.getMovieById(id);
		movie.setPath(domain+movie.getPath());
		Integer number = redisDao.getMovieNumber(movie.getId()+"");
		if(number==null){
			redisDao.setMovieNumber(movie.getId()+"",movie.getNumber()+"");
		}else{
			movie.setNumber(number);
		}
		return movie;
	}

	@Override
	public void addMovie(Movie movie) {
		movieDao.save(movie);
	}

	@Override
	public List<BasicInfo> getAllBasicInfo() {
		List<Movie> list = movieDao.queryAll();
		List<BasicInfo> basicInfos =  new ArrayList<BasicInfo>(); 
		for (Movie movie : list) {
			movie.setPath(domain+movie.getPath());
			Integer number = redisDao.getMovieNumber(movie.getId()+"");
			if(number==null){
				redisDao.setMovieNumber(movie.getId()+"",movie.getNumber()+"");
			}else{
				movie.setNumber(number);
			}
			basicInfos.add((BasicInfo)movie);
		}
		return basicInfos;
	}
	
	@Override
	public Integer getMovieNumber(int id) {
		Integer number = redisDao.getMovieNumber(id+"");
		if(number==null){
			Movie movie =  movieDao.getMovieById(id);
			redisDao.setMovieNumber(id+"",movie.getNumber()+"");
			return movie.getNumber();
		}else{
			return number;
		}
	}
	
	
	@Transactional
	public void updateNumber(int id){
		Movie movie = movieDao.getMovieById(id);
		movie.setNumber(movie.getNumber()-1);
		System.out.println(movie.getNumber());
		movieDao.update(movie);
	}

	
	@Transactional
	@Override
	/**
	 * 使用消息队列
	 */
	public boolean movieGT2(int id,MUser user) throws Exception {
		redisDao.lpush("movie-uid",id+":",user.getId());
		return true;
	}
	
	@Override
	public void processSubMessage() {
		while(true){
			String value = redisDao.lpop("movie-uid");
			if(value==null) continue;
			
			String[] values = value.split(":");
			String mid = values[0];
			String uid = values[1];
			
			MOrder order = new MOrder();
			order.setUid(uid);
			order.setMid(Integer.valueOf(mid));
			
			try {
				Integer number = redisDao.getMovieNumber(mid+"");
				if(number==null){
					Movie movie =  movieDao.getMovieById(Integer.valueOf(mid));
					number = movie.getNumber();
					redisDao.setMovieNumber(mid, number+"");
				}
				if(number<=0) {//校验影票数量
					order.setStatus(-1);
					order.setRemark("当前抢购人数较多，影票数量不足");
				}else{//更新票数
					try {
						number--;
						redisDao.setMovieNumber(mid, number+"");
						order.setStatus(1);
						order.setRemark("恭喜抢到一张影票");
						
						//发送邮件
						try {
							Movie movie =  movieDao.getMovieById(Integer.valueOf(mid));
							MUser user = mUserDao.selectById(uid);
							//发送邮件
							String content=EmailService.EMAIL_CONTEXT1+""
									+ "<a href=\""+domain+"/movie?id="+mid+"\">"+movie.getName()+"</a><img src=\""+domain+movie.getPath()+"\"/>"
									+ ""+EmailService.EMAIL_CONTEXT2;
							emailService.send(user.getEmail(),EmailService.EMAIL_TITLE, content);
							order.setStatus(2);
							order.setRemark("恭喜抢到一张影票，已发送邮件");
						} catch (Exception e) {
							e.printStackTrace();
							order.setStatus(-3);
							order.setRemark("恭喜抢到一张影票，邮件发送失败，联系管理员");
						}
						
					} catch (Exception e) {
						order.setStatus(-2);
						order.setRemark("服务器出错");
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				order.setStatus(-2);
				order.setRemark("服务器出错");
				e.printStackTrace();
			}finally {//保存订单
				order.setDate(new Date());
				movieDao.saveOrder(order);
			}
		}
		
	}
	
	
	@Transactional
	@Override
	public synchronized boolean movieGT(int id,MUser user) throws Exception {
		MOrder order = new MOrder();
		order.setUid(user.getId());
		order.setMid(id);
		try {
			Integer number = redisDao.getMovieNumber(id+"");
			if(number==null){
				Movie movie =  movieDao.getMovieById(id);
				number = movie.getNumber();
				redisDao.setMovieNumber(id+"", number+"");
			}
			if(number<=0) {//校验影票数量
				order.setStatus(-1);
				order.setRemark("当前抢购人数较多，影票数量不足");
			}else{//更新票数
				try {
					number--;
					redisDao.setMovieNumber(id+"", number+"");
					order.setStatus(1);
					order.setRemark("恭喜抢到一张影票");
					
					//发送邮件
					try {
						Movie movie =  movieDao.getMovieById(id);
						//发送邮件
						String content=EmailService.EMAIL_CONTEXT1+""
								+ "<a href=\""+domain+"/movie?id="+id+"\">"+movie.getName()+"</a><img src=\""+domain+movie.getPath()+"\"/>"
								+ ""+EmailService.EMAIL_CONTEXT2;
						emailService.send(user.getEmail(),EmailService.EMAIL_TITLE, content);
						order.setStatus(2);
						order.setRemark("恭喜抢到一张影票，已发送邮件");
					} catch (Exception e) {
						e.printStackTrace();
						order.setStatus(-3);
						order.setRemark("恭喜抢到一张影票，邮件发送失败，联系管理员");
					}
					
				} catch (Exception e) {
					order.setStatus(-2);
					order.setRemark("服务器出错");
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			order.setStatus(-2);
			order.setRemark("服务器出错");
			e.printStackTrace();
		}finally {//保存订单
			movieDao.saveOrder(order);
		}
		return true;
	}

	@Override
	public MOrder getOrder(String uid, Integer mid) {
		MOrder order = new MOrder();
		order.setMid(mid);
		order.setUid(uid);
		return movieDao.selectOrder(order);
	}

	@Override
	public List<String> getMovieImage(int mid) {
		List<ImageInfo> infos = movieDao.selectImageInfo(mid);
		List<String> paths = new ArrayList<>();
		for (ImageInfo imageInfo : infos) {
			paths.add(domain+imageInfo.getPath());
		}
		return paths;
	}

}
