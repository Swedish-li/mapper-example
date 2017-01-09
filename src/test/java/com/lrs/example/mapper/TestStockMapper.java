package com.lrs.example.mapper;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lrs.example.model.Stock;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestStockMapper {
	@Resource
	private StockMapper mapper;

	@Test
	public void testQuery() {
		List<Stock> list = mapper.selectAll();
		System.out.println(list);
	}
}
