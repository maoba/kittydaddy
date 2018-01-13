package com.kittydaddy.app;

public class MyRunnable implements Runnable{

	@Override
	public void run() {
		synchronized (this) {
			 for (int x = 0; x < 100; x++) {
			     System.out.println(Thread.currentThread().getName() + ":" + x);
	        }
		}
	}

}
