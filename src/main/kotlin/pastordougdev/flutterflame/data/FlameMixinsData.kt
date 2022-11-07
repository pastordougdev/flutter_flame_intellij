package pastordougdev.flutterflame.data

import pastordougdev.flutterflame.models.FlameMixin
import pastordougdev.flutterflame.models.FlameMixinType

val flameMixins = listOf(
    FlameMixin( "ParentIsA", 1, FlameMixinType.component, 2),
    FlameMixin( "HasGameRef", 1, FlameMixinType.component, 2),
    FlameMixin( "HasGameReference", 1, FlameMixinType.component, 128),
    FlameMixin( "HasPaint", 1, FlameMixinType.component,2),
    FlameMixin( "ComponentViewportMargin", 0, FlameMixinType.component,2),
    FlameMixin( "Draggable", 0, FlameMixinType.gesture,2),
    FlameMixin( "Tappable", 0, FlameMixinType.gesture,2),
    FlameMixin( "Hoverable", 0, FlameMixinType.gesture,2),
    FlameMixin( "GestureHitboxes", 0, FlameMixinType.gesture,2),
    FlameMixin( "HasAncestor", 1, FlameMixinType.component, 2),
    FlameMixin( "HasDecorator", 0, FlameMixinType.component, 2),
    FlameMixin("TapCallbacks", 0, FlameMixinType.input,16),
    FlameMixin( "DragCallbacks", 0, FlameMixinType.input, 16),
    FlameMixin( "KeyboardHandler", 0, FlameMixinType.input, 2),
    FlameMixin( "SingleChildParticle", 0, FlameMixinType.component, 2),
    FlameMixin( "CollisionCallbacks", 0, FlameMixinType.component, 4),
    FlameMixin("FlameBlocReader", 2, FlameMixinType.other, 64),
    FlameMixin("FlameBlocListenable", 2, FlameMixinType.other, 64),
    FlameMixin("FlameSendSignals", 0, FlameMixinType.other, 512),
    FlameMixin("FlameSignalListenable", 0, FlameMixinType.other, 512),
    FlameMixin("FlameIsolate", 0, FlameMixinType.other, 256),
    FlameMixin("Terrain", 0, FlameMixinType.other, 256),
    FlameMixin("ColonistObject", 0, FlameMixinType.other, 256),
    FlameMixin("Moveable", 0, FlameMixinType.other, 256),

);