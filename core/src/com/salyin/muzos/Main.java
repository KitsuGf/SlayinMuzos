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

	private OrthogonalTiledMapRenderer renderer; //Clase auxiliar para renderizar un mapa.
	private OrthographicCamera camera; //Cámara a través de la que veremos el mundo.
	public static final float unitScale = 1/16f; //Nos servirá para establecer que la pantalla se divide en tiles de 32 pixeles;
	private static int WIDTH; //Aquí almacenaremos la anchura en tiles
	private static int HEIGHT; //Aquí almacenaremos la altura en tiles


	@Override
	public void create () {

		float w = Gdx.graphics.getWidth(); //Obtenemos la anchura de nuestra pantalla
		float h = Gdx.graphics.getHeight(); //Obtenemos la atura de nuestra pantalla
		TiledMap map = new TmxMapLoader().load("maps/rooms/room_castle/castle_room.tmx"); //Cargamos el tilemap desde assets
		renderer = new OrthogonalTiledMapRenderer(map, unitScale); //Establecemos el renderizado del mapa dividido en Tiles de 32 dp.
		camera = new OrthographicCamera(); //Declaramos la cámara a través de la que veremos el mundo
		//camera.zoom=0.80f; //Establecemos el zoom de la cámara. 0.1 es más cercano que 1. Jugaremos con esto en clase
		WIDTH = ((TiledMapTileLayer) map.getLayers().get(0)).getWidth(); //Obtenemos desde el mapa el número de tiles de ancho de la 1º Capa
		HEIGHT = ((TiledMapTileLayer) map.getLayers().get(0)).getHeight(); //Obtenemos desde el mapa el número de tiles de alto de la 1º Capa
		Gdx.app.log("Width",Float.toString(WIDTH)); //Sacamos por el log el número de tiles de ancho
		Gdx.app.log("Height",Float.toString(HEIGHT)); //Sacamos por el log el número de tiles de alto
		camera.setToOrtho(false, WIDTH,HEIGHT); //Establecemos la cámara, y le decimos cuanto tiene que ocupar.
		Gdx.app.log("camera x",Float.toString(camera.position.x));
		Gdx.app.log("camera x",Float.toString(camera.position.y));
		camera.position.y = Math.max(camera.position.y -6f,0);
		//camera.position.x = Math.max(camera.position.x +4,0);
		camera.update(); //Colocamos la Cámara

	}

	@Override
	public void render () {
		renderer.setView(camera); //Establecemos la vista del mundo a través de la cámara.
		renderer.render(); //Renderizamos la vista

	}
	
	@Override
	public void dispose () {
		renderer.dispose(); //Destruimos el objeto que renderiza un mapa, para no tener filtraciones de memoria
	}
}
