package pastordougdev.flutterflame.models

import pastordougdev.flutterflame.data.flamePackages
import pastordougdev.flutterflame.misc.camelToSnakeCase

class FlameComponentBuilder (
    val component: FlameComponent,
    val className: String,
    val classGenerics: String,
    val mixins: List<ChosenFlameMixin>,
    val overrides: Map<String, Boolean>,
    val superProperties: List<String>
        ) {

    private var flamePackagesCounter = 0

    fun getFlameComponent(): String {

        println("Super Properties: $superProperties")
        val buffer = StringBuffer()
        val imports = getPackages()
        for(imp in imports) {
            buffer.append("import '$imp';\n")
        }
        buffer.append("\n")
        buffer.append(classBody())

        return buffer.toString()
    }

    fun getClassDeclaration() : String {
        val classBuffer = StringBuffer()
        classBuffer.append("class ")
        classBuffer.append(className)
        classBuffer.append(" extends ")
        classBuffer.append(component.description)

        //* generic types on base class
        if(classGenerics.isNotEmpty()) {
            val cGens = classGenerics.split(",")
            val cGens2 = cGens.map { g -> g.trim()}
            val cg = cGens2.joinTo(classBuffer, ", ", "<", ">")
        }
        //*
        if(mixins.isNotEmpty()) {
            classBuffer.append(" with ")
            val allMixins = mixins.map { m -> m.getMixin()}
            allMixins.joinTo(classBuffer, ", ")
        }
        return classBuffer.toString()
    }

    fun classBody(): String {
        val bodyBuffer = StringBuffer()
        bodyBuffer.append(getClassDeclaration())
        bodyBuffer.append(" {\n\n")

        bodyBuffer.append(buildConstructor())

        if(overrides["onLoad"] == true) {
            bodyBuffer.append("\n")
            bodyBuffer.append(onLoad())
        }

        if(overrides["onMount"] == true) {
            bodyBuffer.append("\n")
            bodyBuffer.append(onMount())
        }

        if(overrides["update"] == true) {
            bodyBuffer.append("\n")
            bodyBuffer.append(update())
        }

        if(overrides["render"] == true) {
            bodyBuffer.append("\n")
            bodyBuffer.append(render())
        }

        if(overrides["onRemove"] == true) {
            bodyBuffer.append("\n")
            bodyBuffer.append(onRemove())
        }

        bodyBuffer.append("\n}")
        return bodyBuffer.toString()
    }

    fun buildConstructor() : String {
        val buffer = StringBuffer()
        buffer.append("$className(")
        //buffer.append("(")
        if(superProperties.isNotEmpty()) {
            val sProps = superProperties.map { sp -> "super.$sp"}
            sProps.joinTo(buffer, ", ", "{", "}")
        }
        buffer.append(");\n\n")
        return buffer.toString()
    }

    fun fileName() : String {
        return camelToSnakeCase(className)
    }

    fun getPackages() : List<String> {
        val pList = mutableListOf<String>()
        if(overrides["render"] == true) {
            pList.add("dart:ui")
        }

        flamePackagesCounter = flamePackagesCounter or component.flamePackage

        for(mixin in mixins) {
            flamePackagesCounter = flamePackagesCounter or mixin.flamePackage
            println("${mixin.name} counter now $flamePackagesCounter")
        }
        flamePackages.forEach { p ->
            if(p.key and flamePackagesCounter == p.key) {
                pList.add(p.value)
            }
        }

        return pList
    }

    fun onLoad(): String {
        return """
        @override
        Future<void> onLoad() async {
            //TODO: implement onLoad
        }"""
    }

    fun onMount(): String {
        return """
        @override
        void onMount() {
            super.onMount();
            //TODO: implement onMount
        }
        """
    }

    fun update(): String {
        return """
        @override
        void update(double dt) {
            //TODO: implement update
        }
        """
    }

    fun render(): String {
        return """
        @override
        void render(Canvas canvas) {
            //TODO: implement render
        }
        """
    }

    fun onRemove(): String {
        return """
        @override
        void onRemove() {
            super.onRemove();
            //TODO: implement onRemove
        }
        """
    }
}