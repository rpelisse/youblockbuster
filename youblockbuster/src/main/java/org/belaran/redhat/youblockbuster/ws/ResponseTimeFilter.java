package org.belaran.redhat.youblockbuster.ws;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class ResponseTimeFilter implements Filter {

	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		long startTime = System.currentTimeMillis();
		chain.doFilter(request, response);
		System.out.println("HttpCall executed in:" + (System.currentTimeMillis() - startTime) +"ms");
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {}

}
