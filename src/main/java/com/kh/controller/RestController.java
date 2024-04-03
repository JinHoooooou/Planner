package com.kh.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

public interface RestController {

  JSONObject execute(HttpServletRequest request, HttpServletResponse response);
}
