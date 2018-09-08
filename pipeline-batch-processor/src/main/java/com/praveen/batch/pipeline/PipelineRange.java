package com.praveen.batch.pipeline;

import java.util.ArrayList;
import java.util.List;

import com.praveen.commons.utils.ToStringUtils;

public class PipelineRange {

	private Number minRange;
	private Number maxRange;

	public PipelineRange(Number minRange, Number maxRange) {
		this.minRange = minRange;
		this.maxRange = maxRange;
	}

	public Number getMinRange() {
		return minRange;
	}

	public void setMinRange(Number minRange) {
		this.minRange = minRange;
	}

	public Number getMaxRange() {
		return maxRange;
	}

	public void setMaxRange(Number maxRange) {
		this.maxRange = maxRange;
	}

	public static List<PipelineRange> createRange(Long min, Long max, int parts) {
		List<PipelineRange> res = new ArrayList<>();
		long start = min;
		long interval = (max - min) / parts;
		long end;

		for (int i = 0; i < parts; i++) {
			end = start + interval;
			if (end >= max) {
				end = max;
			}
			res.add(new PipelineRange(start, end));
			start = end + 1;
		}
		return res;

	}

	public static List<PipelineRange> createRange(int parts) {
		List<PipelineRange> res = new ArrayList<>();
		for (int i = 0; i < parts; i++) {
			res.add(new PipelineRange(1, 1));
		}
		return res;

	}

	@Override
	public String toString() {
		return ToStringUtils.asString(this, "minRange", "maxRange");
	}

}
