package com.pluseq.mire.runtime;

import java.lang.reflect.InvocationTargetException;

import org.mozilla.javascript.*;
import org.mozilla.javascript.ast.Scope;
import org.mozilla.javascript.tools.shell.Environment;
import org.mozilla.javascript.tools.shell.Global;
import org.mozilla.javascript.tools.shell.ShellContextFactory;

import com.pluseq.mire.core.World;

/**
 * World context describes the 
 * @author or
 */
public class WorldContext extends ScriptableObject implements ContextAction{
	private static final long serialVersionUID = -7801562795757609292L;
	
	public World world;
	protected Context jsContext;
	protected Scriptable jsScope;
	
	public static ShellContextFactory shellContextFactory = new ShellContextFactory();
	public static Global global = new Global();
	

	public WorldContext () {
		this(new World("default"));
	}

	public WorldContext (World world) {
		this.world = world;
	}
	

	/**
	 * @return js context
	 * @throws InvocationTargetException 
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 */
	public Context getJSContext() throws IllegalAccessException, InstantiationException, InvocationTargetException {
		if (null == jsContext) {
			jsContext = Context.enter();
			jsContext.setLanguageVersion(Context.VERSION_1_2);
			//jsContext.setOptimizationLevel(EMPTY);
			//jsContext.setApplicationClassLoader(null);
			/*
			Environment.defineClass(this);
	        Environment environment = new Environment(this);
	        defineProperty("environment", environment,
                    ScriptableObject.DONTENUM);
            */
		}
		return jsContext;
	}
	
	
	public Scriptable getJSScope() throws IllegalAccessException, InstantiationException, InvocationTargetException {
		if (null == jsScope) {
			Context cx = getJSContext();
			jsScope = cx.initStandardObjects(this, false);
			Object jsOut = Context.javaToJS(new World("test"), jsScope);
			ScriptableObject.putProperty(jsScope, "world", jsOut);

		}
		return jsScope;
	}
	
	public void yell() {
		System.out.println("okokok");
	}
	
	public Object evalJS(String code) throws IllegalAccessException, InstantiationException, InvocationTargetException {
		return getJSContext().evaluateString(getJSScope(), code, "test", 1, null);
	}

	@Override
	public String getClassName() {
		return "WorldContext";
	}

	@Override
	public Object run(Context arg0) {
		// TODO Auto-generated method stub
		return null;
	}
}
