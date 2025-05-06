package com.outsera.csvreader.dto;

import java.util.List;

public class AwardIntervalResponseDTO {

	private List<ProducerIntervalDTO> min;

	private List<ProducerIntervalDTO> max;

	public List<ProducerIntervalDTO> getMin() {
		return min;
	}

	public void setMin(List<ProducerIntervalDTO> min) {
		this.min = min;
	}

	public List<ProducerIntervalDTO> getMax() {
		return max;
	}

	public void setMax(List<ProducerIntervalDTO> max) {
		this.max = max;
	}

}
