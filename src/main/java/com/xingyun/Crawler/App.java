package com.xingyun.Crawler;

import com.xingyun.Crawler.main.MainQueue;

/**
 * 爬虫入口！！
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        MainQueue queue = new MainQueue();
        queue.go();
    	
    }
}
