package rutebaga.model.entity.npc;

import rutebaga.model.entity.Entity;

public class Speech {

	private String speech;
	private Entity speaker;
	
	public Speech(Entity speaker, String speech) {
		this.speaker = speaker;
		this.speech = speech;
	}

	public String getSpeech() {
		return speech;
	}

	public void setSpeech(String speech) {
		this.speech = speech;
	}

	public Entity getSpeaker() {
		return speaker;
	}

	public void setSpeaker(Entity speaker) {
		this.speaker = speaker;
	}
	
}
