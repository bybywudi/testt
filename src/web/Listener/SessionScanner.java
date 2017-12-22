package web.Listener;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionIdListener;
import javax.servlet.http.HttpSessionListener;

/**
 * Application Lifecycle Listener implementation class SessionScanner
 *
 */


@WebListener
public class SessionScanner implements HttpSessionListener, HttpSessionAttributeListener, HttpSessionActivationListener, HttpSessionBindingListener, HttpSessionIdListener,ServletContextListener {
	
	private List list = Collections.synchronizedList(new LinkedList());//调用该方法可以让List是线程安全的，不会发生两个对象抢一个位置的情况，列表也是单线程的
	private Object lock = new Object();//该Lock的作用是将多个代码放到线程安全的代码块内，几段代码共享同一个Lock，如果有一个线程在使用lock，则其他要用到Lock的对象要等待
	static final int SESSION_EXSIT_TIME = 5*60*1000;
	
    public SessionScanner() {
        // TODO Auto-generated constructor stub
    }

    public void sessionCreated(HttpSessionEvent se)  { 
         HttpSession session = se.getSession();
         System.out.println("创建！！！！！！！");
         synchronized(lock) {
        	 list.add(session);
         }
    }

    public void valueBound(HttpSessionBindingEvent arg0)  { 
         // TODO Auto-generated method stub
    }

    public void sessionIdChanged(HttpSessionEvent arg0, String arg1)  { 
         // TODO Auto-generated method stub
    }


    public void sessionDestroyed(HttpSessionEvent arg0)  { 
    	System.out.println("摧毁！！！！！！！！！！");
    }


    public void sessionDidActivate(HttpSessionEvent arg0)  { 
         // TODO Auto-generated method stub
    }


    public void attributeAdded(HttpSessionBindingEvent arg0)  { 
         // TODO Auto-generated method stub
    }


    public void attributeRemoved(HttpSessionBindingEvent arg0)  { 
         // TODO Auto-generated method stub
    }


    public void attributeReplaced(HttpSessionBindingEvent arg0)  { 
         // TODO Auto-generated method stub
    }


    public void sessionWillPassivate(HttpSessionEvent arg0)  { 
         // TODO Auto-generated method stub
    }


    public void valueUnbound(HttpSessionBindingEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		Timer timer = new Timer();
		timer.schedule(new MyTask(list,lock), 0, SESSION_EXSIT_TIME);
	}
	
	
}
class MyTask extends TimerTask{
	private List list;
	private Object lock;
	public MyTask(List list,Object lock) {
		this.list = list;
		this.lock = lock;
	}
	public void run() {
		synchronized(this.lock) {
			ListIterator it = list.listIterator();
			while(it.hasNext()) {
				HttpSession session = (HttpSession) it.next();                                                                                                                                     
				if(System.currentTimeMillis() - session.getLastAccessedTime() > SessionScanner.SESSION_EXSIT_TIME) {
					session.invalidate();//getLastAccessedTime可以得到session的上次访问时间，判断和系统当前时间的差异，如果大于5分钟则摧毁掉
					//list.remove(session);在集合迭代器迭代的时候如果删除元素，会抛出并发修改异常。
					//如果要在迭代的过程中进行增删改查，要使用iterator的子类ListIterator的方法
					//但也会出现问题，比如在迭代器正在迭代的过程中如果又有用户访问，此时向List中添加了数据，但是迭代器并不知道，此时会出现并发异常
					it.remove();
				}
			}
		}

	}
}