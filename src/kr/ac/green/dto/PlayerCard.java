package kr.ac.green.dto;

import java.util.Arrays;

public class PlayerCard {
	private int player_code;
	private int skill_level;
	private int left_foot;
	private int right_foot;
	private int player_pay;
	private int player_height;
	private int player_weight;
	private String player_name;
	private Position[] positions;
	private Long[] bps;
	private String srcThumb;

	public PlayerCard() {
	}

	public PlayerCard(int player_code, int skill_level, int left_foot,
			int right_foot, int player_pay, int player_height,
			int player_weight, String player_name, Position[] positions) {
		this.player_code = player_code;
		this.skill_level = skill_level;
		this.left_foot = left_foot;
		this.right_foot = right_foot;
		this.player_pay = player_pay;
		this.player_height = player_height;
		this.player_weight = player_weight;
		this.player_name = player_name;
		this.positions = positions;
	}

	public PlayerCard(int player_code, int skill_level, int left_foot,
			int right_foot, int player_pay, int player_height,
			int player_weight, String player_name) {
		this.player_code = player_code;
		this.skill_level = skill_level;
		this.left_foot = left_foot;
		this.right_foot = right_foot;
		this.player_pay = player_pay;
		this.player_height = player_height;
		this.player_weight = player_weight;
		this.player_name = player_name;
	}

	public PlayerCard(int player_code, String player_name,
			Position[] positionArray, Long[] bps, String srcThumb) {
		this.player_code = player_code;
		this.player_name = player_name;
		this.positions = positionArray;
		this.bps = bps;
		this.srcThumb = srcThumb;
	}

	@Override
	public String toString() {
		return "PlayerCard [player_code=" + player_code + ", player_name="
				+ player_name + ", positions=" + Arrays.toString(positions)
				+ ", bps=" + Arrays.toString(bps) + ", srcThumb=" + srcThumb
				+ "]";
	}

	public Position[] getPositions() {
		return positions;
	}

	public void setPositions(Position[] positions) {
		this.positions = positions;
	}

	public int getPlayer_code() {
		return player_code;
	}

	public void setPlayer_code(int player_code) {
		this.player_code = player_code;
	}

	public void setBps(int idx, Long player_price) {
		this.bps[idx] = player_price;
	}
	
	public Long[] getBps() {
		return bps;
	}

	public void setBps(Long[] bps) {
		this.bps = bps;
	}

	public int getSkill_level() {
		return skill_level;
	}

	public void setSkill_level(int skill_level) {
		this.skill_level = skill_level;
	}

	public int getLeft_foot() {
		return left_foot;
	}

	public void setLeft_foot(int left_foot) {
		this.left_foot = left_foot;
	}

	public int getRight_foot() {
		return right_foot;
	}

	public void setRight_foot(int right_foot) {
		this.right_foot = right_foot;
	}

	public int getPlayer_pay() {
		return player_pay;
	}

	public void setPlayer_pay(int player_pay) {
		this.player_pay = player_pay;
	}

	public int getPlayer_height() {
		return player_height;
	}

	public void setPlayer_height(int player_height) {
		this.player_height = player_height;
	}

	public int getPlayer_weight() {
		return player_weight;
	}

	public void setPlayer_weight(int player_weight) {
		this.player_weight = player_weight;
	}

	public String getPlayer_name() {
		return player_name;
	}

	public void setPlayer_name(String player_name) {
		this.player_name = player_name;
	}

	public String getSrcThumb() {
		return srcThumb;
	}

	public void setSrcThumb(String srcThumb) {
		this.srcThumb = srcThumb;
	}
}