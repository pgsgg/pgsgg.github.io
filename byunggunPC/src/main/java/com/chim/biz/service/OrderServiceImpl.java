package com.chim.biz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chim.biz.dao.CartDAO;
import com.chim.biz.dao.OrderDAO;
import com.chim.biz.dto.CartVO;
import com.chim.biz.dto.OrderVO;

@Service("orderService")
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderDAO orderDAO;
	@Autowired
	private CartDAO cartDAO;
	
	@Override
	public int selectMaxOseq() {
		
		return orderDAO.selectMaxOseq();
	}

	@Override
	public void insertOrder(OrderVO vo) {
		// TODO Auto-generated method stub
		orderDAO.insertOrder(vo);
	}

	@Override
	public int insertOrderDetail(OrderVO vo) {
		// TODO Auto-generated method stub
		
		int oseq = this.selectMaxOseq();
		vo.setOseq(oseq);
		
		this.insertOrder(vo);
		
		List<CartVO> cartList = cartDAO.selectCart(vo.getId());
		
		for(CartVO cart : cartList) {
			OrderVO order = new OrderVO();
			
			order.setOseq(oseq);
			order.setPseq(cart.getPseq());
			order.setQuantity(cart.getQuantity());
			
			orderDAO.insertOrderDetail(order);
			cartDAO.updateCart(cart.getCseq());
		}
		return oseq;
	}

	@Override
	public List<OrderVO> listOrderById(OrderVO vo) {
		// TODO Auto-generated method stub
		return orderDAO.listOrderById(vo);
	}

	@Override
	public List<Integer> selectSeqOrdering(OrderVO vo) {
		// TODO Auto-generated method stub
		return orderDAO.selectSeqOrdering(vo);
	}

	@Override
	public void deleteOrder(int oseq) {
		// TODO Auto-generated method stub	
		orderDAO.deleteOrder(oseq);
	}
	

}
