package com.outsera.csvreader.factory;

import com.outsera.csvreader.dto.ProducerIntervalDTO;

public class ProducerIntervalFactory {
	
	public static ProducerIntervalDTO build(Long followingWin, Long previousWin, Long interval, String producer) {
		ProducerIntervalDTO producerIntervalDTO = new ProducerIntervalDTO();
		producerIntervalDTO.setFollowingWin(followingWin);
		producerIntervalDTO.setPreviousWin(previousWin);
		producerIntervalDTO.setInterval(interval);
		producerIntervalDTO.setProducer(producer);
		return producerIntervalDTO;
	}
}
