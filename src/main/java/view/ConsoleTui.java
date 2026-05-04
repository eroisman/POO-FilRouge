package view;

import allShared.IConsoleTui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Petit renderer console sans dependance externe pour afficher
 * des scenarios de test sous forme de TUI ASCII.
 */
public final class ConsoleTui implements IConsoleTui {

    private static final int DEFAULT_BOX_WIDTH = 112;
    private static final int MIN_BOX_WIDTH = 80;
    private static final int MAX_BOX_WIDTH = 220;

    private static final int BOX_WIDTH = resolveBoxWidth();
    private static final int[] CHECK_WIDTHS = resolveCheckWidths(BOX_WIDTH);

    @Override
    public void title(String title, String subtitle) {
        List<String> lines = new ArrayList<String>();
        if (subtitle != null && !subtitle.trim().isEmpty()) {
            lines.add(subtitle);
        }
        System.out.println(renderBox(title, lines, '='));
    }

    @Override
    public void section(String title) {
        System.out.println();
        System.out.println(renderBox(title, Collections.<String>emptyList(), '-'));
        System.out.println(buildTableBorder());
        System.out.println(buildRow("Type", "Operation", "Resultat", "Attendu"));
        System.out.println(buildTableBorder());
    }

    @Override
    public void check(String operation, Object result, String expected) {
        System.out.println(buildRow("CHECK", operation, stringify(result), defaultValue(expected)));
    }

    @Override
    public void info(String operation, Object result) {
        System.out.println(buildRow("INFO", operation, stringify(result), "-"));
    }

    @Override
    public void note(String title, String message) {
        System.out.println();
        System.out.println(renderBox(title, wrap(message, BOX_WIDTH - 4), '-'));
    }

    static String renderPanel(String title, List<String> lines) {
        return ConsoleTui.renderBox(title, lines, '-');
    }

    static String renderBanner(String title, String subtitle) {
        List<String> lines = subtitle == null || subtitle.trim().isEmpty()
                ? Collections.<String>emptyList()
                : List.of(subtitle);
        return ConsoleTui.renderBox(title, lines, '=');
    }
    private static String renderBox(String title, List<String> lines, char borderChar) {
        StringBuilder builder = new StringBuilder();
        builder.append(boxBorder(borderChar)).append('\n');
        builder.append(boxLine(title == null ? "" : title.toUpperCase())).append('\n');
        if (lines != null) {
            for (String line : lines) {
                for (String wrappedLine : wrap(line, BOX_WIDTH - 4)) {
                    builder.append(boxLine(wrappedLine)).append('\n');
                }
            }
        }
        builder.append(boxBorder(borderChar));
        return builder.toString();
    }

    private static String buildTableBorder() {
        StringBuilder builder = new StringBuilder("+");
        for (int width : CHECK_WIDTHS) {
            builder.append(repeat('-', width + 2)).append('+');
        }
        return builder.toString();
    }

    private static String buildRow(String type, String operation, String result, String expected) {
        return new StringBuilder()
                .append("| ").append(pad(type, CHECK_WIDTHS[0])).append(" | ")
                .append(pad(operation, CHECK_WIDTHS[1])).append(" | ")
                .append(pad(result, CHECK_WIDTHS[2])).append(" | ")
                .append(pad(expected, CHECK_WIDTHS[3])).append(" |")
                .toString();
    }

    private static String boxBorder(char borderChar) {
        return "+" + repeat(borderChar, BOX_WIDTH - 2) + "+";
    }

    private static String boxLine(String content) {
        return "| " + pad(content, BOX_WIDTH - 4) + " |";
    }

    private static String pad(String value, int width) {
        String safeValue = defaultValue(value).replace('\n', ' ').replace('\r', ' ').trim();
        if (safeValue.length() > width) {
            return safeValue.substring(0, Math.max(0, width - 3)) + "...";
        }
        StringBuilder builder = new StringBuilder(safeValue);
        while (builder.length() < width) {
            builder.append(' ');
        }
        return builder.toString();
    }

    private static String stringify(Object value) {
        return value == null ? "null" : String.valueOf(value);
    }

    private static String defaultValue(String value) {
        return value == null || value.trim().isEmpty() ? "-" : value;
    }

    private static String repeat(char value, int count) {
        char[] chars = new char[count];
        Arrays.fill(chars, value);
        return new String(chars);
    }

    private static List<String> wrap(String text, int width) {
        if (text == null || text.trim().isEmpty()) {
            return Collections.singletonList("");
        }

        List<String> lines = new ArrayList<String>();
        String[] rawLines = text.replace('\r', '\n').split("\n");
        for (String rawLine : rawLines) {
            if (rawLine.length() <= width) {
                lines.add(rawLine);
                continue;
            }

            String remaining = rawLine.trim();
            while (remaining.length() > width) {
                int splitAt = remaining.lastIndexOf(' ', width);
                if (splitAt <= 0) {
                    splitAt = width;
                }
                lines.add(remaining.substring(0, splitAt).trim());
                remaining = remaining.substring(splitAt).trim();
            }
            if (!remaining.isEmpty()) {
                lines.add(remaining);
            }
        }

        return lines;
    }

    private static int resolveBoxWidth() {
        String raw = System.getProperty("tui.width");
        if (raw == null || raw.trim().isEmpty()) {
            return DEFAULT_BOX_WIDTH;
        }

        try {
            int parsed = Integer.parseInt(raw.trim());
            if (parsed < MIN_BOX_WIDTH) {
                return MIN_BOX_WIDTH;
            }
            if (parsed > MAX_BOX_WIDTH) {
                return MAX_BOX_WIDTH;
            }
            return parsed;
        } catch (NumberFormatException ignored) {
            return DEFAULT_BOX_WIDTH;
        }
    }

    private static int[] resolveCheckWidths(int boxWidth) {
        // Table width is the sum of content widths + 13 characters for separators/padding.
        int targetContentWidth = Math.max(0, boxWidth - 13);

        int[] min = {6, 22, 22, 14};
        int minSum = min[0] + min[1] + min[2] + min[3];
        if (targetContentWidth <= minSum) {
            return min;
        }

        int extra = targetContentWidth - minSum;
        int[] weights = {8, 44, 44, 24};
        int weightTotal = 120;

        int[] widths = {
                min[0] + (extra * weights[0] / weightTotal),
                min[1] + (extra * weights[1] / weightTotal),
                min[2] + (extra * weights[2] / weightTotal),
                min[3] + (extra * weights[3] / weightTotal)
        };

        int used = widths[0] + widths[1] + widths[2] + widths[3];
        int remainder = targetContentWidth - used;
        int[] distributionOrder = {1, 2, 3, 0};
        int index = 0;
        while (remainder > 0) {
            widths[distributionOrder[index % distributionOrder.length]]++;
            remainder--;
            index++;
        }

        return widths;
    }
}
