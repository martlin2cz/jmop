package cz.martlin.jmop.core.operation.base;

/**
 * Twoset operation is just simply two operations run in row. The result of
 * first is passed as input of the second.
 * 
 * @author martin
 *
 * @param <IT>
 *            input type
 * @param <MT>
 *            middle type (result of first and input of second)
 * @param <OT>
 *            output type
 */
public class TwosetOperation<IT, MT, OT> implements BaseOperation<IT, OT> {

	private final BaseOperation<IT, MT> first;
	private final BaseOperation<MT, OT> second;

	/**
	 * Creates instance.
	 * 
	 * @param first
	 * @param second
	 */
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
