package auxiliary;

import javax.swing.SwingUtilities;
import javax.swing.JComponent;

import java.awt.Graphics;

import javax.sound.sampled.TargetDataLine;

public class LevelMeter extends JComponent {
	private static final long serialVersionUID = 1L;


	private float amp = 0f;

	public void setAmplitude(float amp) {
		this.amp = Math.abs(amp);
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		int size = 17;
		int spacing = 0;
		for (int z = 0; z < 5; z++) {
			int y = 0;
			for (int i = 0; i < (int) (this.amp*100)/2 - 22; i++) {
				g.fill3DRect(spacing, getHeight()-size-y, size+5, size, false);
				y += size+5;
			}
			spacing += 30;
		}
	}

	public static class Record implements Runnable {
		final LevelMeter meter; 
		private TargetDataLine line;
		public Record(final LevelMeter meter, TargetDataLine line) {
			this.meter = meter;
			this.line = line;
		}

		@Override
		public void run() {
			final int bufferByteSize = 2048;

			byte[] buf = new byte[bufferByteSize];
			float[] samples = new float[bufferByteSize / 2];

			for(int b; (b = line.read(buf, 0, buf.length)) > -1;) {

				// convert bytes to samples here
				for(int i = 0, s = 0; i < b;) {
					int sample = 0;

					sample |= buf[i++] & 0xFF; // (reverse these two lines
					sample |= buf[i++] << 8;   //  if the format is big endian)
						

					// normalize to range of +/-1.0f
					samples[s++] = sample / 32768f;

				}

				float rms = 0f;
				for(float sample : samples) {

					rms += sample * sample;
				}

				rms = (float)Math.sqrt(rms / samples.length);
				setMeterOnEDT(rms);
				/*try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				meter.repaint();
			}
		}

		void setMeterOnEDT(final float rms) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					meter.setAmplitude(rms);
					meter.repaint();
				}
			});
		}
	}
}

