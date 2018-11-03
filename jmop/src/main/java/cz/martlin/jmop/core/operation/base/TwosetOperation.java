package cz.martlin.jmop.core.operation.base;

public class TwosetOperation<IT, MT, OT> implements BaseOperation<IT, OT> {

	private final BaseOperation<IT, MT> first;
	private final BaseOperation<MT, OT> second;

	public TwosetOperation(BaseOperation<IT, MT> first, BaseOperation<MT, OT> second) {
		super();
		this.first = first;
		this.second = second;
	}

	@Override
	public OT run(IT input, OperationChangeListener handler) {
		MT mid = first.run(input, handler);
		return second.run(mid, handler);
	}

}
