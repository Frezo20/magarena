package magic.ui.screen.card.explorer;

import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import magic.data.MagicIcon;
import magic.translate.MText;
import magic.ui.FontsAndBorders;
import magic.ui.helpers.ImageHelper;
import magic.ui.screen.widget.ActionBarButton;
import magic.ui.screen.widget.BigDialButton;
import magic.ui.screen.widget.IDialButtonHandler;
import magic.ui.widget.cards.table.CardsTableStyle;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
class OptionsPanel extends JPanel {

    // translatable UI text (prefix with _S).
    private static final String _S1 = "Layout";
    private static final String _S2 = "Style";

    private static final ImageIcon MENU_ICON =
            ImageHelper.getRecoloredIcon(MagicIcon.OPTION_MENU, Color.BLACK, Color.WHITE);

    private boolean isMenuOpen = false;
    private final BigDialButton layoutButton;
    private final BigDialButton styleButton;
    private final ActionBarButton menuButton;
    private final ActionBarButton closeButton;
    private final ExplorerScreen screen;

    OptionsPanel(final ExplorerScreen screen) {

        this.screen = screen;

        layoutButton = new BigDialButton(getLayoutHandler());
        styleButton = new BigDialButton(getStyleHandler());

        menuButton = new ActionBarButton(MENU_ICON, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doToggleMenuOptions();
            }
        });

        closeButton = new ActionBarButton((ImageIcon) MENU_ICON, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doToggleMenuOptions();
            }
        });

        setLayout(new MigLayout(
                "flowy, wrap 2, gap 0 2, insets 0 0 2 0, ax right, ay center"
        ));
        setLayout();

        setOpaque(false);
    }

    private IDialButtonHandler getStyleHandler() {
        return new IDialButtonHandler() {
            @Override
            public int getDialPositionsCount() {
                return CardsTableStyle.values().length;
            }
            @Override
            public int getDialPosition() {
                return CardsTableStyle.getStyle().ordinal();
            }
            @Override
            public boolean doLeftClickAction(int dialPosition) {
                CardsTableStyle.setNextStyle();
                screen.setCardsTableStyle();
                return true;
            }
            @Override
            public boolean doRightClickAction(int dialPosition) {
                return false; // not supported.
            }
            @Override
            public void onMouseEntered(int dialPosition) {
                // not supported
            }
        };
    }

    private IDialButtonHandler getLayoutHandler() {
        return new IDialButtonHandler() {
            @Override
            public int getDialPositionsCount() {
                return ExplorerScreenLayout.values().length;
            }
            @Override
            public int getDialPosition() {
                return ExplorerScreenLayout.getLayout().ordinal();
            }
            @Override
            public boolean doLeftClickAction(int dialPosition) {
                screen.doSwitchLayout();
                return true;
            }
            @Override
            public boolean doRightClickAction(int dialPosition) {
                return false; // not supported.
            }
            @Override
            public void onMouseEntered(int dialPosition) {
                // not supported.
            }
        };
    }

    private void setLayout() {
        removeAll();
        if (isMenuOpen) {
            add(getLabel(MText.get(_S1)), "ax center, w 60!");
            add(layoutButton, "ax center, h 24!, w 24!, gapbottom 2");
            add(getLabel(MText.get(_S2)), "ax center, w 60!");
            add(styleButton, "ax center, h 24!, w 24!, gapbottom 2");
            add(closeButton, "spany 2, h 32!, w 32!");
        } else {
            add(menuButton, "spany 2, h 32!, w 32!");
        }
        revalidate();
        repaint();
    }

    private void doToggleMenuOptions() {
        isMenuOpen = !isMenuOpen;
        setLayout();
    }

    private JLabel getLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(FontsAndBorders.FONT0);
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        return lbl;
    }
}
