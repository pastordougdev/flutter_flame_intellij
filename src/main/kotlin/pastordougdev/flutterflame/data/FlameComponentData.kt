package pastordougdev.flutterflame.data

import pastordougdev.flutterflame.models.FlameComponent

val flameComponents = listOf(
    FlameComponent("PositionComponent", 0, 2),
    FlameComponent("Entity", 0, 32),
    FlameComponent("Behavior", 2, 32),
    FlameComponent("SpriteComponent", 0, 2),
    FlameComponent("SpriteBatchComponent", 0, 2),
    FlameComponent("SpriteGroupComponent", 1, 2),
    FlameComponent("SpriteAnimationComponent", 0, 2),
    FlameComponent("Route", 0, 2),
    FlameComponent("OverlayRoute", 0, 2),
    FlameComponent("ValueRoute", 1, 2),
    FlameComponent("RouterComponent", 0, 2),
    FlameComponent("SvgComponent", 0, 16384),
    FlameComponent("FlareActorComponent", 0, 1024),
    FlameComponent("ParallaxComponent", 1, 2),
    FlameComponent("ParticleSystemComponent", 0, 2),
    FlameComponent("RectangleComponent", 0, 2048),
    FlameComponent("CircleComponent", 0, 2048),
    FlameComponent("PolygonComponent", 0, 2048),
    FlameComponent("IsometricTileMapComponent", 0, 2),
    FlameComponent("NineTileBoxComponent", 0, 2),
    FlameComponent("CustomPainterComponent", 0, 2),
    FlameComponent("ClipComponent", 0, 2),
    FlameComponent("TextComponent", 0, 2),
    FlameComponent("TextBoxComponent", 0, 2),
    FlameComponent("TimerComponent", 0, 2),
    FlameComponent("CameraComponent", 0, 128),
    FlameComponent("FlameBlocReader", 0, 64),
    FlameComponent("FlameBlocProvider", 0, 64),
    FlameComponent("FlameMultiBlocProvider", 0, 64),
    FlameComponent("BodyComponent", 1, 4096),
    FlameComponent("RiveComponent", 0, 8192),
    FlameComponent("TiledComponent", 1, 32768),
)

val flameComponentSuperProperties = listOf(
    "position", "size", "scale", "priority", "angle", "nativeAngle", "anchor", "children"
)