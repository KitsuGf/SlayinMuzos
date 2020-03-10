package Tools;


import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.salyin.muzos.Main;

/**
 *
 * @Author Kitsu ( Juan Miguel )
 *
 *  WorldCreator create the collisions from the TileMap
 *
 */
public class WorldCreator {

    public WorldCreator(World world, TiledMap map){

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        // Foreach to search floor collider and show it in game.
        for (MapObject object : map.getLayers().get(10).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+ rect.getWidth() / 2)/ Main.ppm, (rect.getY() + rect.getHeight() / 2)/Main.ppm);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() /2 /Main.ppm, rect.getHeight() / 2 /Main.ppm);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //For each to search left block and show in the game.
        for (MapObject object : map.getLayers().get(11).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+ rect.getWidth() / 2)/Main.ppm, (rect.getY() + rect.getHeight() / 2)/Main.ppm);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() /2 /Main.ppm, rect.getHeight() / 2 /Main.ppm);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        //For each to search Right block and show in the game.
        for (MapObject object : map.getLayers().get(12).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+ rect.getWidth() / 2)/Main.ppm, (rect.getY() + rect.getHeight() / 2)/Main.ppm);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() /2 /Main.ppm, rect.getHeight() / 2 /Main.ppm);
            fdef.shape = shape;
            body.createFixture(fdef);
        }


    }


}
