package com.pluseq.mire.runtime;

import java.io.*;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeJavaObject;

import com.pluseq.mire.core.*;
import com.pluseq.mire.storage.*;
import com.pluseq.vivid.*;
import com.pluseq.vivid.mysql.*;


public class MireConsole {
	
	public static void main(String[] args) throws Exception {
		while (true) {
			System.out.print("> ");
			
			InputStreamReader converter = new InputStreamReader(System.in);
			BufferedReader in = new BufferedReader(converter);
	
			String input = in.readLine();
			
			if (input.equals("q") || input.equals("quit")) {
				return;
			}
			
			if (input.substring(0, 3).equals("js ")) {
				World w = new World("Arkanar");
		        WorldContext wc = new WorldContext(w);

		        Object a = wc.evalJS(input.substring(3));
		        if (a instanceof NativeJavaObject) {
		        	a = Context.toString(a);
		        }
		        System.out.println(a.toString());
			}
			
			if (input.equals("session")) {
				VividTableRegistry tr = Vivid.getTableRegistry();
				VividSqlConnectionInterface con = new VividMySQLConnection();
				//tr.addTable(new SessionMapTable());
				tr.addTable(new SessionSqlTable(con));
				tr.addTable(new LocationTable(con));
				tr.addTable(new UserSqlTable(con));
				
				MireSession session = new MireSession("kpogorelov");
				SessionHolder sh = new SessionHolder(session);
				sh.save();
				System.out.println("saved - " + sh.get().getLogin());
				
				String id = sh.get().getSessionId();
				
				sh = new SessionHolder(new MireSession("another"));
				String anotherId = sh.get().getSessionId();
				sh.save();
				System.out.println("saved - " + sh.get().getLogin());
			
				sh = (SessionHolder)MireStorage._().getSessionTable().holderById(id);
				if (null == sh) {
					System.out.println("session test failed, cant get expected session from storage");
				} else {
					System.out.println("ok - " + sh.get().getLogin());
				}
				
				System.out.println("another session: " + anotherId);
				sh = (SessionHolder)MireStorage._().getSessionTable().holderById(anotherId);
				sh.get().setLogin("vasiliy");
				sh.save();
				
				Location l = new Location();
				l.title = "Ukraine";
				//l.id = 17;
				LocationHolder lh = new LocationHolder(l);
				lh.save();
				
				MireUser u = new MireUser();
				u.login = "kpogorelov";
				u.password = "asdasd";
				u.email = "some@internet.com";
				u.name  = "Der Name";
				UserHolder uh = new UserHolder(u);
				uh.save();
				uh = (UserHolder) MireStorage._().getUserTable().holderById("kpogorelov");
				System.out.println("Name is: " + uh.get().name);
				if (!((AbstractSqlTable)MireStorage._().getUserTable()).delete(uh)) {
					System.out.println("Cannot delete");
				}
			}
			
			//System.out.println("You typed: " + input);
		}
	}
}
