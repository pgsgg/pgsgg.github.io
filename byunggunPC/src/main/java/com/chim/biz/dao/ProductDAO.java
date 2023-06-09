package com.chim.biz.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chim.biz.dto.ProductVO;

import utils.Criteria;

@Repository
public class ProductDAO {

	@Autowired
	private SqlSessionTemplate mybatis;

	public List<ProductVO> selectProduct() {

		return mybatis.selectList("ProductMapper.selectProduct");
	}

	public ProductVO selectProductDetail(int pseq) {

		return mybatis.selectOne("ProductMapper.selectProductDetail", pseq);
	}

	public List<ProductVO> getlistProductWithPaging(Criteria criteria, String name) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("criteria", criteria);
		map.put("name", name);
		return mybatis.selectList("ProductMapper.listProductWithPaging", map);
	}

	public int countProductList(String name) {

		return mybatis.selectOne("ProductMapper.countProductList", name);
	}

	/*
	 * 상품 재고 관련 메소드
	 */
	// quantity는 product 테이블 컬럼
	public void updateQuantity(ProductVO vo) {
		mybatis.update("ProductMapper.updateQuantity", vo);
	}

	public void increaseQuantity(ProductVO vo) {
		mybatis.update("ProductMapper.increaseQuantity", vo);
	}

	public void updateSellable(ProductVO vo) {
		mybatis.update("ProductMapper.updateSellable", vo);
	}

	public int getProductQuantity(int pseq) {
		return mybatis.selectOne("ProductMapper.getProductQuantity", pseq);
	}

	/*
	 * 상품 재고 관련 메소드 끝
	 */
	public List<ProductVO> getProductListByKind(String kind) {

		return mybatis.selectList("ProductMapper.getProductListByKind", kind);
	}

	public List<ProductVO> getProductListByKindWithPaging(String kind, Criteria criteria) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("kind", kind);
		map.put("criteria", criteria);

		return mybatis.selectList("ProductMapper.getProductListByKindWithPaging", map);
	}

	public int countProductListByKind(String kind) {

		return mybatis.selectOne("ProductMapper.countProductListByKind", kind);
	}

	public List<ProductVO> getProductListByBestynWithPaging(String bestyn, Criteria criteria) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("bestyn", bestyn);
		map.put("criteria", criteria);

		return mybatis.selectList("ProductMapper.getProductListByBestynWithPaging", map);
	}

	public int countProductListByBestyn(String bestyn) {

		return mybatis.selectOne("ProductMapper.countProductListByBestyn", bestyn);
	}

	public List<ProductVO> searchProdcutList(String name, Criteria criteria) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("name", name);
		map.put("criteria", criteria);

		return mybatis.selectList("ProductMapper.searchProdcutList", map);
	}

	public void updateProduct(ProductVO vo) {

		mybatis.update("ProductMapper.updateProduct", vo);
	}

	public void insertProduct(ProductVO vo) {

		mybatis.insert("ProductMapper.insertProduct", vo);
	}

	public ProductVO getProduct(ProductVO vo) {

		return mybatis.selectOne("ProductMapper.getProduct", vo);
	}

	public int countSearchProduct(String name) {

		return mybatis.selectOne("ProductMapper.countSearchProduct", name);
	}

	public void deleteProduct(ProductVO vo) {

		mybatis.delete("ProductMapper.deleteProduct", vo);
	}
//	<select id="countProductList" parameterType="String" resultType="int">
//	getlistProductWithPaging
//	countProductList
//	<!-- 상품목록 조회 -->
//	<select id="listProduct" parameterType="String" resultType="product">
//
//	<!-- 페이지별 상품 목록 조회 -->
//	<select id="listProductWithPaging" resultType="product">

}
