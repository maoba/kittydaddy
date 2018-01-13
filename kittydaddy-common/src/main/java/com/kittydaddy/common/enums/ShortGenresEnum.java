package com.kittydaddy.common.enums;

/**
 * @author kitty daddy
 * 短视频分类枚举值
 */
public enum ShortGenresEnum {
     AMUSE("搞笑"),
     MUSIC("音乐"),
     MICRO_FILM("微电影"),
     START_CONSULT("明星资讯"),
     MOVIE_CONSULT("电影资讯"),
     TV_CONSULT("电视资讯"),
     MUSIC_CONSULT("音乐资讯"),
     NEWS("新闻"),
     SPORTS("体育"),
     GAME_LOL("英雄联盟"),
     GAME_CF("穿越火线"),
     GAME_MS("魔兽世界"),
     GAME_DNF("地下城与勇士"),
     GAME_LITTLE_STAR("最终幻想"), 
     GAME_TLBB("天龙八部"),
     GAME_JL("剑灵"),
     GAME_MHXY("梦幻西游");
	
     private String value;

	 private ShortGenresEnum(String value) {
		this.value = value;
	 }

	 public String getValue() {
	 	return value;
	 }

	 public void setValue(String value) {
		this.value = value;
	 }
     
     
}
