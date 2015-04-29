/*
 * Copyright (C) 2010-2015 - JavocSoft - Javier Gonzalez Serrano
 * http://javocsoft.es/proyectos/code-libs/android/javocsoft-toolbox-android-library
 * 
 * This file is part of JavocSoft Android Toolbox library.
 *
 * JavocSoft Android Toolbox library is free software: you can redistribute it 
 * and/or modify it under the terms of the GNU General Public License as 
 * published by the Free Software Foundation, either version 3 of the License, 
 * or (at your option) any later version.
 *
 * JavocSoft Android Toolbox library is distributed in the hope that it will be 
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General 
 * Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JavocSoft Android Toolbox library.  If not, see 
 * <http://www.gnu.org/licenses/>.
 * 
 */
package es.javocsoft.android.lib.toolbox.widgets;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * A TextView type widget that accepts HTML tags in
 * the text  string.<br><br>
 * 
 * NOTE:
 * 	Not all HTML tags are supported officially. Supported ones are:<br><br>
 * 
 * &lt;a href=”…”&gt;,&lt;b&gt;,&lt;big&gt;,&lt;blockquote&gt;,&lt;br&gt;,&lt;cite&gt;,&lt;dfn&gt;,
 * &lt;div align=”…”&gt;,&lt;em&gt;,&lt;font size=”…” color=”…” face=”…”&gt;,&lt;h1&gt;,
 * &lt;h2&gt;,&lt;h3&gt;,&lt;h4&gt;,&lt;h5&gt;,&lt;h6&gt;,&lt;i&gt;,&lt;img src=”…”&gt;,&lt;p&gt;,
 * &lt;small&gt;,&lt;strike&gt;,&lt;strong&gt;,&lt;sub&gt;,&lt;sup&gt;,&lt;tt&gt;,&lt;u&gt;<br><br>
 * 
 * @author JavocSoft 2014
 * @version 1.0
 *
 */
public class HTMLStyledTextView extends TextView
{
    public HTMLStyledTextView(Context context) {
        super(context);
    }

    public HTMLStyledTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HTMLStyledTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setText(CharSequence text, BufferType type)
    {
       super.setText(Html.fromHtml(text.toString()), type);
    }
}