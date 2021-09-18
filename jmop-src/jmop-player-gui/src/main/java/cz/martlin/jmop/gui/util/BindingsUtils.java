package cz.martlin.jmop.gui.util;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.util.Duration;

public class BindingsUtils {
	public static class DoubleMilisToDurationBinding extends ObjectBinding<Duration> {

		private final DoubleProperty milisProperty;

		public DoubleMilisToDurationBinding(DoubleProperty milisProperty) {
			super();

			this.milisProperty = milisProperty;

			bind(milisProperty);
		}

		@Override
		protected Duration computeValue() {
			double milis = milisProperty.get();
			return milisToDuration(milis);
		}

	}

	public static class DurationToDoubleMilisBinding extends DoubleBinding {

		private final ReadOnlyObjectProperty<Duration> durationProperty;

		public DurationToDoubleMilisBinding(ReadOnlyObjectProperty<Duration> durationProperty) {
			super();

			this.durationProperty = durationProperty;

			bind(durationProperty);
		}

		@Override
		protected double computeValue() {
			Duration duration = durationProperty.get();
			return durationToMilis(duration);
		}

	}

	public static double durationToMilis(Duration duration) {
		if (duration == null) {
			return 0.0;
		}

		return duration.toMillis();
	}

	public static Duration milisToDuration(double milis) {
		return new Duration(milis);
	}

}
