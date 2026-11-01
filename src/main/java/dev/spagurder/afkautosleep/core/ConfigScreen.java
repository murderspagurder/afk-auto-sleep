package dev.spagurder.afkautosleep.core;

//? if >=26.2 {
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
//?} else {
/*import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
*///?}
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ConfigScreen extends Screen {

    private final Screen parent;
    private Checkbox logoutWhenNoBed;
    private Checkbox onlyWhenOtherPlayersOnline;
    private EditBox logoutMessage;
    private EditBox sleepMessages;

    public ConfigScreen(Screen parent) {
        super(Component.translatable("screen.afkautosleep.config.title"));
        this.parent = parent;
    }

    public static void open(Minecraft client) {
        ConfigScreen screen = new ConfigScreen(null);
        //? if >=26.2 {
        client.setScreenAndShow(screen);
        //?} else {
        /*client.setScreen(screen);
        *///?}
    }

    @Override
    protected void init() {
        int center = this.width / 2;
        this.logoutWhenNoBed = Checkbox.builder(
                        Component.translatable("screen.afkautosleep.config.logoutWhenNoBed"),
                        this.font
                )
                .pos(center - 100, 50)
                .selected(ClientConfig.logoutWhenNoBed())
                .build();
        this.addRenderableWidget(this.logoutWhenNoBed);

        this.logoutMessage = new EditBox(
                this.font,
                center - 100,
                90,
                200,
                20,
                Component.translatable("screen.afkautosleep.config.logoutMessage")
        );
        this.logoutMessage.setMaxLength(256);
        this.logoutMessage.setValue(ClientConfig.logoutMessage());
        this.addRenderableWidget(this.logoutMessage);

        this.onlyWhenOtherPlayersOnline = Checkbox.builder(
                Component.translatable("screen.afkautosleep.config.onlyWhenOtherPlayersOnline"),
                this.font
            )
            .pos(center - 100, 120)
            .selected(ClientConfig.onlyWhenOtherPlayersOnline())
            .build();
        this.addRenderableWidget(this.onlyWhenOtherPlayersOnline);

        this.sleepMessages = new EditBox(
            this.font,
            center - 100,
            160,
            200,
            20,
            Component.translatable("screen.afkautosleep.config.sleepMessages")
        );
        this.sleepMessages.setMaxLength(2048);
        this.sleepMessages.setValue(String.join(" | ", ClientConfig.sleepMessages()));
        this.addRenderableWidget(this.sleepMessages);

        this.addRenderableWidget(Button.builder(
                        Component.translatable("gui.done"),
                        button -> save()
                )
            .pos(center - 100, this.height - 30)
                .width(95)
                .build());
        this.addRenderableWidget(Button.builder(
                        Component.translatable("gui.cancel"),
                        button -> closeWithoutSaving()
                )
                .pos(center + 5, this.height - 30)
                .width(95)
                .build());
    }

    //? if >=26.2 {
    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.centeredText(this.font, this.title, this.width / 2, 20, 0xFFFFFF);
        guiGraphics.text(
                this.font,
                Component.translatable("screen.afkautosleep.config.logoutMessage"),
                this.width / 2 - 100,
                78,
                0xFFFFFF
        );
            guiGraphics.text(
                this.font,
                Component.translatable("screen.afkautosleep.config.sleepMessages"),
                this.width / 2 - 100,
                148,
                0xFFFFFF
            );
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTick);
    }
    //?} else {
    /*@Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFF);
        guiGraphics.drawString(
                this.font,
                Component.translatable("screen.afkautosleep.config.logoutMessage"),
                this.width / 2 - 100,
                78,
                0xFFFFFF
        );
            guiGraphics.drawString(
                this.font,
                Component.translatable("screen.afkautosleep.config.sleepMessages"),
                this.width / 2 - 100,
                148,
                0xFFFFFF
            );
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }
    *///?}

    @Override
    public void onClose() {
        closeWithoutSaving();
    }

    private void save() {
        ClientConfig.save(
            this.logoutWhenNoBed.selected(),
            this.logoutMessage.getValue(),
            ClientConfig.parseSleepMessagesText(this.sleepMessages.getValue()),
            this.onlyWhenOtherPlayersOnline.selected()
        );
        closeScreen();
    }

    private void closeWithoutSaving() {
        closeScreen();
    }

    private void closeScreen() {
        //? if >=26.2 {
        this.minecraft.setScreenAndShow(this.parent);
        //?} else {
        /*this.minecraft.setScreen(this.parent);
        *///?}
    }

}