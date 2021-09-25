package cz.martlin.jmop.player.cli.repl.mixin;

import java.util.List;

import cz.martlin.jmop.player.cli.repl.misc.AbstractJMOPPicocliComponent;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import picocli.CommandLine.IDefaultValueProvider;
import picocli.CommandLine.Model.ArgSpec;
import picocli.CommandLine.Model.ITypeInfo;
import picocli.CommandLine.Model.PositionalParamSpec;

@Deprecated
public class CurrentDefaultProvider extends AbstractJMOPPicocliComponent implements IDefaultValueProvider {

	public CurrentDefaultProvider(JMOPPlayer jmop) {
		super(jmop);
	}

	@Override
	public String defaultValue(ArgSpec argSpec) throws Exception {
		throw new UnsupportedOperationException("do not use me");
//		if (argSpec instanceof PositionalParamSpec) {
//			PositionalParamSpec new_name = (PositionalParamSpec) argSpec;
//			List<String> osv = new_name.originalStringValues();
//			ITypeInfo type = new_name.typeInfo();
//			
//			System.out.println(osv);
//			System.out.println(type);
//			
//			
//		}
//		System.out.println(argSpec);
//		// TODO Auto-generated method stub
//		return null;
	}

}
