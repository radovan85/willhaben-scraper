package com.radovan.spring.services.impl;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.radovan.spring.services.SoundService;

@Service
public class SoundServiceImpl implements SoundService {

	@Override
	public void playSound(String soundFileName) {
		try {
			ClassPathResource resource = new ClassPathResource("audio/" + soundFileName);
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(resource.getInputStream());
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.start();
			// Sleep da se zvuk reproducira do kraja
			Thread.sleep(clip.getMicrosecondLength() / 1000);
			clip.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}