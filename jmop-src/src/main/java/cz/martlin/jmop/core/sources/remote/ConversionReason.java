package cz.martlin.jmop.core.sources.remote;

public enum ConversionReason {
	CONVERT("Converting"), PREPARE_TO_PLAY("Preparing");

	private final String humanName;

	private ConversionReason(String humanName) {
		this.humanName = humanName;
	}

	public String getHumanName() {
		return humanName;
	}
}
