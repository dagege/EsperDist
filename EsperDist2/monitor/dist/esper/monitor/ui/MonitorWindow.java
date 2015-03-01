package dist.esper.monitor.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;

import dist.esper.io.GlobalStat;
import dist.esper.io.KryoFileWriter;
import dist.esper.monitor.ui.old.StatComposite;
import dist.esper.monitor.ui.old.WorkerInstancesComposite;

public class MonitorWindow {
	Display display;
	Shell shell;
	GlobalStat gs;
	int width=1200;
	int height=700;
	StatComposite mc=null;
	StatComposite2 mc2=null;
	EventComposite ec=null;
	WorkerInstancesComposite wcc=null;
	WorkerInstancesComposite2 wcc2=null;
	CTabFolder tabFolder=null;
	
	public static void main(String[] args){
		String filePath="globalstat.bin";
		GlobalStat gs=(GlobalStat)KryoFileWriter.readFromFile(filePath);
		MonitorWindow win=new MonitorWindow();
		win.init();
		win.update(gs);
		win.display();
	}
	
	public MonitorWindow(){
		super();
	}
	
	public Display getDisplay(){
		return display;
	}
	
	public void setSubmitEplHook(SubmitEplHook submitEplHook) {
		ec.setSubmitEplHook(submitEplHook);
	}
	
	public void update(GlobalStat gs){
		//mc.update(gs);
		mc2.update(gs);
		ec.update(gs);
		//wcc.update(gs);
		wcc2.update(gs);
	}
	
	public void display(){
		shell.open();
		while (!shell.isDisposed()) {
			try{
				while (!display.readAndDispatch()) {
					display.sleep();
				}
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
		display.dispose();
	}
	
	public void init(){
		display=new Display();
		shell=new Shell(display);
		shell.setSize(width, height);
		shell.setLayout(new FillLayout());
		
		tabFolder=new CTabFolder(shell, SWT.NONE);
		tabFolder.setTabHeight(20);
		
		CTabItem ecTabItem=new CTabItem(tabFolder, SWT.NONE);
		ecTabItem.setText("Event Panel");
		ec=new EventComposite(tabFolder, width-10, height-30);
		ec.init();
		//ec.update(gs);
		ecTabItem.setControl(ec.getComposite());
		
		CTabItem wccTabItem=new CTabItem(tabFolder, SWT.NONE);
		wccTabItem.setText("Instance Panel");
//		wcc=new WorkerInstancesComposite(tabFolder, width-10, height-30);
//		wcc.init();
//		//wcc.update(gs);
//		wccTabItem.setControl(wcc.getComposite());
		
		wcc2=new WorkerInstancesComposite2(tabFolder, width-10, height-30);
		wcc2.init();
		wccTabItem.setControl(wcc2.getComposite());
		
		CTabItem mcTabItem=new CTabItem(tabFolder, SWT.NONE);
		mcTabItem.setText("Worker Monitoring Panel");
//		mc=new StatComposite(tabFolder, width-10, height-30);
//		mc.init();
//		//mc.update(gs);
//		mcTabItem.setControl(mc.getComposite());
		mc2=new StatComposite2(tabFolder, width-10, height-30);
		mc2.init();
		//mc.update(gs);
		mcTabItem.setControl(mc2.getComposite());
	}
	
//	class UpdateUIRunnable implements Runnable{
//		@Override
//		public void run() {			
//			while(!shell.isDisposed() && !display.isDisposed()){
//				if(mc!=null){
//					mc.update();
//				}
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {					
//					e.printStackTrace();
//				}
//			}
//		}
//	}
}