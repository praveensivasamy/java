package com.journal.controller;
/**
 * 
 * @author lenovo
 *
 */

//@Controller
public class TestController {

    //@RequestMapping("/test")
    public String welcome() {
	return "view";
    }

    //@RequestMapping("/test1")
    public String thankyou() {
	return "thankyou";
    }

}
