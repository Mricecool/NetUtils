package com.netutils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import rx.Observable;
import rx.Scheduler;
import rx.Observable.OnSubscribe;
import rx.Scheduler.Worker;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observables.ConnectableObservable;
import rx.schedulers.Schedulers;

public class RxJavaActivity extends Activity implements OnClickListener {

	private Button btn, btnMap, btnFlatMap, btnFilter, btnConnect, btnTimer, btnMerge, btnSchuder, btnSort, btnTake,
			btnBinding;
	private TextView txt;
	private Integer[] num = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	private Subscription subscription = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rxjava);
		btn = (Button) findViewById(R.id.btn);
		txt = (TextView) findViewById(R.id.txt);
		btn.setOnClickListener(this);
		findViewById(R.id.btnMap).setOnClickListener(this);
		findViewById(R.id.btnFlatMap).setOnClickListener(this);
		findViewById(R.id.btnFilter).setOnClickListener(this);
		findViewById(R.id.btnConnect).setOnClickListener(this);
		findViewById(R.id.btnTimer).setOnClickListener(this);
		findViewById(R.id.btnMerge).setOnClickListener(this);
		// 线程调度
		findViewById(R.id.btnSchuder).setOnClickListener(this);
		// 排序
		findViewById(R.id.btnSort).setOnClickListener(this);
		findViewById(R.id.btnTake).setOnClickListener(this);
		/*
		 * RxBinding这个库是基于RxJava的对于Android原生组件的绑定，是RxJava风格的，
		 * 相当于代替了OnClick,Listener这些东西，
		 */
		findViewById(R.id.btnBinding).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn:
			create2();
			break;
		case R.id.btnMap:
			map();
			break;
		case R.id.btnFlatMap:
			flatMap();
			break;
		case R.id.btnFilter:
			filter();
			break;
		case R.id.btnConnect:
			connect();
			break;
		case R.id.btnTimer:
			timer();
			break;
		case R.id.btnMerge:
			merge();
			break;
		case R.id.btnTake:
			take();
			break;
		default:
			break;
		}

	}

	// 创建被观察者（起点）
	private void createBGCZ() {
		// 正常模式
		@SuppressWarnings("deprecation")
		Observable<String> observable = Observable.create(new OnSubscribe<String>() {

			@Override
			public void call(Subscriber<? super String> subscriber) {
				subscriber.onNext("hello");
				subscriber.onNext("你好");
				subscriber.onNext("正常模式");
				subscriber.onCompleted();
			}

		});
		// 偷懒模式
		Observable observable2 = Observable.just("hello", "nihao", "偷懒模式");
		// 偷懒模式2
		String[] arr = { "hello", "nihao", "偷懒模式2" };
		Observable observable3 = Observable.from(arr);

		observable2.subscribe(createGCZ());
	}

	// 创建观察者
	private Subscriber createGCZ() {
		final StringBuffer sb = new StringBuffer("");
		// 正常模式
		Subscriber subscriber = new Subscriber<String>() {

			@Override
			public void onCompleted() {
				txt.setText(sb.toString());
			}

			@Override
			public void onError(Throwable arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onNext(String arg0) {
				sb.append(arg0).append("\n");

			}
		};

		// 偷懒模式，非正常模式
		Action1<String> action1 = new Action1<String>() {

			@Override
			public void call(String arg0) {
				// TODO Auto-generated method stub

			}
		};

		return subscriber;
	}

	// 流式API直接调用
	private void create2() {
		final StringBuffer sb = new StringBuffer("");

		Observable.just("ON", "OFF", "ON").filter(new Func1<String, Boolean>() {

			@Override
			public Boolean call(String arg0) {
				// TODO Auto-generated method stub
				return !arg0.equals("OFF");
			}
		}).subscribe(new Subscriber<String>() {

			@Override
			public void onCompleted() {
				txt.setText(sb.toString());
			}

			@Override
			public void onError(Throwable arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onNext(String arg0) {
				sb.append(arg0).append("\n");

			}
		});
	}

	// 类型变换
	private void map() {
		txt.setText("");
		Observable.from(num).map(new Func1<Integer, String>() {

			@Override
			public String call(Integer arg0) {
				if (arg0 % 2 == 0) {
					return "我是" + arg0 + "我是2的倍数";
				} else {
					return "我是" + arg0 + "我不是2的倍数";
				}

			}
		}).subscribe(new Subscriber<String>() {

			@Override
			public void onCompleted() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onError(Throwable arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onNext(String arg0) {
				txt.append(arg0 + "\n");
			}
		});
	}

	// 类型变换(循环嵌套)
	private void flatMap() {

		txt.setText("");

		List<Classs> cls = new ArrayList<Classs>();
		Classs cls1 = new Classs();
		List<Student> s1 = new ArrayList<Student>();
		Student stu1 = new Student("小明");
		Student stu2 = new Student("小红");
		s1.add(stu1);
		s1.add(stu2);
		cls1.setStus(s1);
		Classs cls2 = new Classs();
		List<Student> s2 = new ArrayList<Student>();
		Student stu3 = new Student("小蓝");
		Student stu4 = new Student("小紫");
		s2.add(stu3);
		s2.add(stu4);
		cls2.setStus(s2);
		cls.add(cls1);
		cls.add(cls2);

		Observable.from(cls).flatMap(new Func1<Classs, Observable<Student>>() {

			@Override
			public Observable<com.netutils.Student> call(Classs arg0) {
				// TODO Auto-generated method stub
				return Observable.from(arg0.getStus());
			}
		}).subscribe(new Action1<Student>() {

			@Override
			public void call(com.netutils.Student arg0) {
				txt.append(arg0.getName() + "\n");
			}
		});

	}

	// 过滤
	private void filter() {
		txt.setText("");
		Observable.from(num).filter(new Func1<Integer, Boolean>() {

			@Override
			public Boolean call(Integer arg0) {
				// TODO Auto-generated method stub
				return arg0 % 2 == 1;
			}
		}).subscribe(new Action1<Integer>() {

			@Override
			public void call(Integer arg0) {
				txt.append(arg0 + "\n");
			}
		});
	}

	// Observable发送事件1-6，两个观察者同时观察这个Observable
	// 要求：每发出一个事件，观察者A和观察者B都会收到，而不是先把所有的事件发送A,然后再发送给B
	private void connect() {
		txt.setText("");
		// 将一个Observable转换为一个可连接的Observable
		ConnectableObservable observable = Observable.from(num).publish();

		Action1 a1 = new Action1<Integer>() {

			@Override
			public void call(Integer arg0) {
				// TODO Auto-generated method stub
				txt.append("A接收到了" + arg0);
			}
		};

		Action1 a2 = new Action1<Integer>() {

			@Override
			public void call(Integer arg0) {
				// TODO Auto-generated method stub
				txt.append("B接收到了" + arg0);
			}
		};

		observable.subscribe(a1);
		observable.subscribe(a2);
		observable.connect();
	}

	// 定时器
	private void timer() {
		// 创建一个每隔2秒发送一次事件的对象
		// subscription = Observable.interval(2,
		// TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread())
		// .subscribe(new Action1<Long>() {
		//
		// @Override
		// public void call(Long arg0) {
		// txt.setText(arg0 + "");
		// }
		// });

		// 延时3秒后开始，间隔5秒发送
		subscription = Observable.interval(3, 5, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Action1<Long>() {

					@Override
					public void call(Long arg0) {
						txt.setText(arg0 + "");
					}
				});
	}

	// 两个任务并发执行，都执行完毕后再更新数据
	private void merge() {
		txt.setText("");

		Observable o1 = Observable.create(new OnSubscribe<String>() {

			@Override
			public void call(Subscriber<? super String> arg0) {
				try {
					Thread.sleep(2000);
					arg0.onNext("第一个任务执行完毕");
					arg0.onCompleted();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).subscribeOn(Schedulers.newThread());

		Observable o2 = Observable.create(new OnSubscribe<String>() {

			@Override
			public void call(Subscriber<? super String> arg0) {
				try {
					Thread.sleep(5000);
					arg0.onNext("第二个任务执行完毕");
					arg0.onCompleted();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).subscribeOn(Schedulers.newThread());

		Observable.merge(o1, o2).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {

			@Override
			public void onCompleted() {
				// TODO Auto-generated method stub
				txt.append("全部执行完毕");
			}

			@Override
			public void onError(Throwable arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onNext(String arg0) {
				// TODO Auto-generated method stub
				txt.append(arg0 + "\n");
			}
		});

	}

	private void take() {
		txt.setText("");
		Observable.from(num).takeLast(5).take(2).doOnNext(new Action1<Integer>() {

			@Override
			public void call(Integer arg0) {
				arg0 = arg0 * 100;
			}
		}).subscribe(new Action1<Integer>() {

			@Override
			public void call(Integer arg0) {
				// TODO Auto-generated method stub
				txt.append(arg0 + "\n");
			}
		});
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 取消订阅
		if (subscription != null && !subscription.isUnsubscribed()) {
			Log.e("-----", "取消");
			subscription.unsubscribe();
		}
	}

}
