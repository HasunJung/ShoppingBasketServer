package msa.shop.basket.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import msa.shop.basket.domain.BasketInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import redis.clients.jedis.Jedis;

@Controller
public class BasketController {
	private static final Logger logger = LoggerFactory.getLogger(BasketController.class);

	private final Jedis jedis = new Jedis("localhost");

	@RequestMapping(value = "/{userId}", method = RequestMethod.POST)
	public @ResponseBody String setProductToBasket(@PathVariable String userId, HttpServletRequest request) {
		final String productId = request.getParameter("productId");
		final String amount = request.getParameter("amount");
		logger.debug("productId:{}, amount:{}", productId, amount);

		Map<String, String> data = new HashMap<String, String>();
		data.put(productId, amount);
		jedis.hmset(userId, data);

		return "success";
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public @ResponseBody String getBasket(@PathVariable String userId) {

		final List<BasketInfo> result = new ArrayList<BasketInfo>();

		Map<String, String> basketList = jedis.hgetAll(userId);
		Iterator<String> it = basketList.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			String value = basketList.get(key);
			logger.debug("ket:{}, value:{}", key, value);
			BasketInfo info = new BasketInfo();
			info.setProductId(key);
			info.setAmount(value);
			result.add(info);
		}
		
		return result.toString();
	}
}
