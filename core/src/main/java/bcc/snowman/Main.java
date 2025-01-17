package bcc.snowman;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

public class Main extends ApplicationAdapter {
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;
    private ArrayList<Body> droppedBalls;
    private ArrayList<Body> pegs;
    private ShapeRenderer shapeRenderer;

    private int frameCount = 0;
    private float WIDTH = 9; // box2d units is 'meters'
    private float HEIGHT = 6;

    private final int ROW_NUM = 15;
    private final int BALLS_IN_FIRST_ROW = 3;
    private final float PEG_AREA_HEIGHT = HEIGHT * .8f;
    private final float PEG_AREA_START = HEIGHT * .9f;
    private final float PEG_CENTER_X = WIDTH / 2;
    private final float TOTAL_WIDTH = WIDTH * .7f;
    private final float SPACING_X = TOTAL_WIDTH / (ROW_NUM - 1 + BALLS_IN_FIRST_ROW - 1);
    private final float SPACING_Y = PEG_AREA_HEIGHT / (ROW_NUM - 1);
    private final float BALL_SIZE = SPACING_Y / 5;
    private final float BOUNCINESS = .4f;
    private final float GRAVITY = 6.5f;

    public void create() {
        world = new World(new Vector2(0, -GRAVITY), true);
        debugRenderer = new Box2DDebugRenderer();
        shapeRenderer = new ShapeRenderer();

        // Set up the camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH, HEIGHT);
        addBorders();
        droppedBalls = new ArrayList<>();
        pegs = new ArrayList<>();

        addBorders();
        addAllPegs();
        dropBall();
    }

    public void addBorders() {
        createWall(WIDTH / 2, 0, WIDTH, .01f); // BOTTOM
        createWall(0, HEIGHT / 2, .01f, HEIGHT); // LEFT
        createWall(WIDTH, HEIGHT / 2, .01f, HEIGHT); // RIGHT
    }

    private void createWall(float centerX, float centerY, float width, float height) {
        BodyDef wallBodyDef = new BodyDef();
        wallBodyDef.position.set(new Vector2(centerX, centerY));

        Body wallBody = world.createBody(wallBodyDef);

        PolygonShape wallShape = new PolygonShape();
        wallShape.setAsBox(width / 2, height / 2);

        wallBody.createFixture(wallShape, 0.0f);

        wallShape.dispose();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        world.step(1 / 60f, 6, 2);
        frameCount++;
        if (frameCount % 60 == 0) dropBall();

        camera.update();

        debugRenderer.render(world, camera.combined);

        renderShapes(droppedBalls);
        renderShapes(pegs);
    }

    private void renderShapes(ArrayList<Body> bodies) {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 1);
        for (Body body : bodies) {
            Vector2 bodyPosition = body.getPosition();
            float bodyAngle = body.getAngle();
            for (Fixture fixture : body.getFixtureList()) {
                if (fixture.getShape() instanceof CircleShape) {
                    CircleShape circleShape = (CircleShape) fixture.getShape();
                    Vector2 localPosition = circleShape.getPosition();
                    localPosition.rotateRad(bodyAngle);
                    localPosition.add(bodyPosition);
                    shapeRenderer.circle(localPosition.x, localPosition.y, circleShape.getRadius(), 30);
                }
            }
        }
        shapeRenderer.end();
    }

    private float randomFloat(float lower, float upper) {
        return lower + (float) Math.random() * (upper - lower);
    }

    private void addAllPegs() {
        for (int row = 0; row < ROW_NUM; row++) {
            int pegsInRow = BALLS_IN_FIRST_ROW + row;
            float startX = PEG_CENTER_X - (pegsInRow - 1) * SPACING_X / 2; // Center the row around PEG_CENTER_X
            float y = PEG_AREA_START - row * SPACING_Y;
    
            for (int col = 0; col < pegsInRow; col++) {
                float x = startX + col * SPACING_X;
                addPeg(x, y, BALL_SIZE);
            }
        }
    }
    
    

    private void addPeg(float x, float y, float radius) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);

        Body peg = world.createBody(bodyDef);

        CircleShape pegShape = new CircleShape();
        pegShape.setRadius(radius);

        FixtureDef pegFixture = new FixtureDef();
        pegFixture.shape = pegShape;
        pegFixture.density = 1f;
        pegFixture.restitution = BOUNCINESS;

        peg.createFixture(pegFixture);

        pegShape.dispose();
        pegs.add(peg);
    }

    private void dropBall() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(PEG_CENTER_X + randomFloat(-.05f, .05f), PEG_AREA_START + SPACING_Y * 1.5f);

        Body ball = world.createBody(bodyDef);

        CircleShape ballShape = new CircleShape();
        ballShape.setRadius(BALL_SIZE);

        FixtureDef ballFixture = new FixtureDef();
        ballFixture.shape = ballShape;
        ballFixture.density = 1f;
        ballFixture.restitution = BOUNCINESS;

        ball.createFixture(ballFixture);

        ballShape.dispose();
        droppedBalls.add(ball);
    }

    @Override
    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
        shapeRenderer.dispose();
    }
}
