package io.paradaux.bukkit.chiton.models.types;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import io.paradaux.bukkit.chiton.models.interfaces.DynamicText;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AnimatedText implements DynamicText {

    private final String fullText;
    private final List<String> frames;
    private final Iterator<String> cycle;

    private String visibleText;

    public AnimatedText(String fullText, String... frames) {
        this.fullText = ChatColor.translateAlternateColorCodes('&', fullText);
        if (frames != null) {
            this.frames = Lists.newArrayList(frames);
        } else {
            this.frames = new ArrayList<>();
        }
        cycle = Iterators.cycle(this.frames);
    }

    public void addColorFlashes(ChatColor color, boolean bold, int howMany) {
        String base = last();
        for (int i = 0; i < howMany; i++) {
            addColorChange(color, bold);
            frames.add(base);
        }
    }

    public void addColorChange(ChatColor color, boolean bold) {
        String text = ChatColor.stripColor(last());
        if (bold) {
            text = ChatColor.BOLD + text;
        }
        text = color + text;
        this.frames.add(text);
    }

    public void addProgressiveColorChange(ChatColor color, boolean bold, ChatColor result) {
        String base = last();
        int lastColorCharIndex = -2;
        Character formatChar1 = null;
        Character formatChar2 = null;
        for (int i = 0; i < base.length(); i++) {
            // color char detected
            if (ChatColor.COLOR_CHAR == base.charAt(i)) {
                lastColorCharIndex = i;
                continue;
            }
            // color char code
            if (i == lastColorCharIndex + 1) {
                formatChar1 = formatChar2;
                formatChar2 = base.charAt(i);
                continue;
            }
            String text = "";
            if (bold) {
                text = ChatColor.BOLD + text;
            }
            text = color + text;
            text = insert(base, text, i, formatChar1, formatChar2);
            if (result != null) {
                if (bold) {
                    text = ChatColor.BOLD + text;
                }
                text = result + text;
            }
            frames.add(text);
        }
        if (result != null) {
            String text = ChatColor.stripColor(last());
            if (bold) {
                text = ChatColor.BOLD + text;
            }
            text = result + text;
            frames.add(text);
        }
    }

    @Override
    public String changeText() {
        if (cycle.hasNext()) {
            visibleText = cycle.next();
        } else {
            visibleText = fullText;
        }
        return visibleText;
    }

    private String last() {
        if (frames.size() > 0) {
            return frames.get(frames.size() - 1);
        }
        return fullText;
    }

    private String insert(String base, String insert, int index, Character format1, Character format2) {
        StringBuilder builder = new StringBuilder(ChatColor.stripColor(base.substring(0, index)));
        builder.append(insert).append(base.charAt(index));
        if (index + 1 < base.length()) {
            if (format1 != null) {
                builder.append(ChatColor.COLOR_CHAR).append(format1);
            }
            if (format2 != null) {
                builder.append(ChatColor.COLOR_CHAR).append(format2);
            }
            builder.append(base.substring(index + 1));
        }
        return builder.toString();
    }

    @Override
    public String getOriginalText() {
        return fullText;
    }

    @Override
    public String getVisibleText() {
        if (visibleText == null) {
            visibleText = fullText;
        }
        return visibleText;
    }

}
