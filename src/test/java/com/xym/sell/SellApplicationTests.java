package com.xym.sell;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.TreeSet;

@RunWith(SpringRunner.class)
@SpringBootTest

public class SellApplicationTests {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Test
	public void test(){
		// 保存字符串
		stringRedisTemplate.opsForValue().set("fuck", "111");
		Assert.assertEquals("111", stringRedisTemplate.opsForValue().get("aaa"));
	}

	@Test
	public void lambda(){
		TreeSet<Integer> treeSet = new TreeSet<>((a,b)->Integer.compare(a,b));
	}
}
