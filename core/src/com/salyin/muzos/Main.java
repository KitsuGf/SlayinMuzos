package com.salyin.muzos;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Main extends ApplicationAdapter {

	TiledMap map;
	OrthographicCamera cam = new OrthographicCamera();
	OrthogonalTiledMapRenderer ren;


	@Override
	public void create () {
		map = new TmxMapLoader().load("maps/rooms/room_air/air_map2.tmx");
		ren = new OrthogonalTiledMapRenderer(map);
		cam.setToOrtho(false, 1920, 640);
	}

	@Override
	public void render () {
		ren.setView(cam);
		ren.render();

	}
	
	@Override
	public void dispose () {

	}
}
