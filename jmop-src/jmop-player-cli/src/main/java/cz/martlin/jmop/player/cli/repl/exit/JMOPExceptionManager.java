package cz.martlin.jmop.player.cli.repl.exit;

import cz.martlin.jmop.core.exceptions.JMOPRuntimeException;
import picocli.CommandLine;
import picocli.CommandLine.IExecutionExceptionHandler;
import picocli.CommandLine.IExitCodeExceptionMapper;
import picocli.CommandLine.IParameterExceptionHandler;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.ParseResult;

public class JMOPExceptionManager
		implements IExecutionExceptionHandler, IExitCodeExceptionMapper, IParameterExceptionHandler {

	public static final int OK = CommandLine.ExitCode.OK;
	public static final int INVALID_USAGE_REJECTED = 201;
	public static final int INVALID_USAGE_PARAMETER = 202;
	public static final int INVALID_USAGE_PARSING_FAILED = 203;
	public static final int FAILED_JMOP_EXCEPTION = 101;
	public static final int FAILED_OTHER_EXCEPTION = 102;

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public int handleExecutionException(Exception ex, CommandLine commandLine, ParseResult parseResult)
			throws Exception {

		printTheException(ex);
		return computeTheReturnCode(ex);
	}

	@Override
	public int handleParseException(ParameterException ex, String[] args) throws Exception {

		printTheException(ex);
		return computeTheReturnCode(ex);
	}

	@Override
	public int getExitCode(Throwable exception) {
		return computeTheReturnCode(exception);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private void printTheException(Exception ex) {
		if ((ex instanceof OperationRejectedException) //
				|| (ex instanceof CommandLine.ParameterException) //
				|| (ex instanceof CommandLine.TypeConversionException)) {

			//TODO customize the error messages for picocli exceptions
			System.err.println(ex.getMessage());
		} else {
			System.err.println("An error occured during the execution of the command: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	private int computeTheReturnCode(Throwable ex) {
		if (ex instanceof CommandLine.ParameterException) {
			return INVALID_USAGE_PARSING_FAILED;
		}
		if (ex instanceof CommandLine.TypeConversionException) {
			return INVALID_USAGE_PARAMETER;
		}
		if (ex instanceof OperationRejectedException) {
			return INVALID_USAGE_REJECTED;
		}

		if (ex instanceof JMOPRuntimeException) {
			return FAILED_JMOP_EXCEPTION;
		}
		if (ex instanceof Throwable) {
			return FAILED_OTHER_EXCEPTION;
		}

		throw new IllegalArgumentException("May never happen");
	}

	public static String errorCodeToString(int code) {
		switch (code) {
		case FAILED_OTHER_EXCEPTION:
			return "FAILED_OTHER_EXCEPTION";
		case FAILED_JMOP_EXCEPTION:
			return "FAILED_JMOP_EXCEPTION";
		case INVALID_USAGE_REJECTED:
			return "INVALID_USAGE_REJECTED";
		case INVALID_USAGE_PARAMETER:
			return "INVALID_USAGE_PARAMETER";
		case INVALID_USAGE_PARSING_FAILED:
			return "INVALID_USAGE_PARSING_FAILED";
		case OK:
			return "OK";
		default:
			throw new IllegalArgumentException("No such code: " + code);
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////
}
