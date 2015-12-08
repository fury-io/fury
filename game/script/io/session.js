with (new JavaImporter(io.github.fury.entity.component)) {
    var system = Game.createSystem("io.session");

    var handleOpenInterfaceMessage = function(entity, message) {
        var session = entity.getComponent(SessionComponent.class);
        // TODO: Write out a built message using the interface data to the session.
    };

    system.subscribe(OpenInterfaceMessage.class,
                     Entity.allComponents(SessionComponent.class),
                     handleOpenInterfaceMessage);
}