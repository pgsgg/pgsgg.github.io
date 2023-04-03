package com.chim.biz.service;

import java.util.List;

import com.chim.biz.dto.OrderVO;

public interface OrderService {

	int selectMaxOseq();

	void insertOrder(OrderVO vo);

	int insertOrderDetail(OrderVO vo);

	List<OrderVO> listOrderById(OrderVO vo);
	
	public List<Integer> selectSeqOrdering(OrderVO vo);
	
	public void deleteOrder(int oseq);

}