
Markdown/HTML converter
-----------------------

Plugin page: [http://artifacts.griffon-framework.org/plugin/markdown](http://artifacts.griffon-framework.org/plugin/markdown)


This plugin provides a facility for converting markdown into HTML, as well as
converting HTML back into markdown. This is a direct port of the [grails-markdown][]
plugin by Ted Naleid.

See [Daring Fireball][] for syntax basics.

## Usage ##

### String Extensions ###

The plugin adds `markdownToHtml()` and `htmlToMarkdown()` methods to the
`java.lang.String`, `java.io.File` and `java.net.URL` classes.

### Service ###

The `markdownService` provides more fine-grained control. It provides property
accessors to the `processor` (an instance of `PegDownProcessor`) and `remark`
(and instance of `Remark`) classes. It also has several methods:

#### Convert Markdown to HTML - `markdown(text, [config])` ####

This method converts markdown into HTML using the Pegdown library. You can optionally
provide an alternate configuration to use instead of the default. (See below for
configuration options.)

#### Convert HTML to Markdown - `htmlToMarkdown(text, [baseUri], [config])` ####

THis method converts HTML (say, from a rich text input) back into Markdown. You
can provide an alternate base URI, and an alternate configuration. (See below for
configuration options.)

#### Sanitize Markdown - `sanitize(text, [config])` ####

This method allows you to clean up markdown provided from untrusted sources. It's
mostly useful if you are allowing the user to include raw HTML with their markdown.

## Dependecies ##

This plugin makes use of the [Pegdown][] and [Remark][] libraries.

## Configuration ##

The `MarkdownProcessor` has a variety of options that you can configure either as
a general option, in `Config.groovy`, or per-usage by providing a `Map` of
options when using the service directly.

Changing the configuration simultaneously configures both the conversion *to* HTML,
as well as converting HTML *back* into Markdown.

The default configuration is almost 100% pure Markdown, with one caveat:

> NOTE: The Markdown engine used in `griffon-markdown` does not allow in-word emphasis.
>
> This means that when you write `an_emphasized_word`, you don't get <code>an<em>emphasized</em>word</code>.
> You just get `an_emphasized_word`. This is true no matter the character used
> (`_` or `*`), or for italics or bold.

#### Hardwraps ####

    markdown.hardwraps = true        // Configuration
    [hardwraps: true]                // Custom Map

Markdown makes simple hardwraps a little difficult, requiring the user to write
two spaces at the end of a line to get a linebreak. This is convenient when
writing in a terminal, but inconvenient if your editor handles soft-wraps internally.

Enabling hardwraps means that all linebreaks are kept.

#### Auto Links ####

    markdown.autoLinks = true        // Configuration
    [autoLinks: true]                // Custom Map

Auto Linking enables conversion of HTTP and HTTPS urls into links without explicit
link generation.

Example Markdown:

    http://griffon-framework.org/

Example HTML:

    <a href="http://griffon-framework.org/">http://griffon-framework.org/</a>

#### Abbreviations ####

    markdown.abbreviations = true    // Configuration
    [abbreviations: true]            // Custom Map

Enables abbreviations are in the [Markdown Extra][] style. These allow the Markdown
output to generate `<abbr>` tags.

Example Markdown:

    This is HTML

    *[HTML]: Hyper-Text Markup Language

Example HTML:

    This is <abbr title="Hyper-Text Markup Language">HTML</abbr>

#### Definition Lists ####

    markdown.definitionLists = true  // Configuration
    [definitionLists: true]          // Custom Map

Enables `<dl>` lists in the [Markdown Extra][] style.

Example Markdown:

    Griffon
    :   Next generation desktop application development platform for the JVM .

Example HTML:

    <dl>
        <dt>Griffon</dt>
        <dd>Next generation desktop application development platform for the JVM.</dd>
    </dl>

#### Smart Quotes, Smart Punctation ####

    markdown.smartQuotes = true      // Configuration
    [smartQuotes: true]              // Custom Map
    markdown.smartPunctuation = true // Configuration
    [smartPunctuation: true]         // Custom Map
    // or, for both use
    markdown.smart = true            // Configuration
    [smart: true]                    // Custom Map

Enables conversion of simple quotes and punctuation into HTML entities and back
again, such as converting `"Foo"` into `“Foo”`, or `---` into `—`.

#### Fenced Code Blocks ####

    markdown.fencedCodeBlocks = true // Configuration
    [fencedCodeBlocks: true]         // Custom Map

Allows the use of several tildes (`~`) to delineate code blocks, instead of
forcing the users to indent each line four spaces.

> Note: If enabled, all code blocks will use fences when converting HTML back
> into Markdown.

#### Tables ####

    markdown.tables = true           // Configuration
    [tables: true]                   // Custom Map

If tables are allowed, you can create tables using [Markdown Extra][] or
[Multimarkdown][] syntax. This also converts tables from HTML *back* into clean,
easy-to-read plain text tables.

An example in Markdown:

    |              |          Grouping           ||
    | First Header | Second Header | Third Header |
    |:------------ |:-------------:| ------------:|
    | Content      |         *Long Cell*         ||
    | Content      |   **Cell**    |         Cell |
    | New Section  |     More      |         Data |
    | And more     |          And more           ||

#### All ####

The `all` option easily enables these items:

 *  Hardwraps
 *  Auto Links
 *  Abbreviations
 *  Definition Lists
 *  Smart Quotes
 *  Smart Punctuation
 *  Fenced Code Blocks
 *  Tables

#### Remove HTML ####

    markdown.removeHtml = true       // Configuration
    [removeHtml: true]               // Custom Map

With this option enabled, all raw HTML will be removed when converting Markdown
to HTML.

#### Remove Tables ####

    markdown.removeTables = true     // Configuration
    [removeTables: true]             // Custom Map

Removes tables when converting HTML to Markdown, instead of leaving them as-is.

#### Base URI ####

    markdown.baseUri = 'http://example.com'

You can override the default base URI (which is determined by your configuration).
The base URI is used when converting relative links.

Setting it to `false` will simply remove relative links.

#### Customize Pegdown ####

    markdown.customizePegdown = { int extensions -> ... }

Allows for customization of the Pegdown extensions before creating a
`PegdownProcessor` using a closure. This closure will be called at the time the
`PegdownProcessor` is first needed, not necessarily at startup.

#### Customize Remark ####

    markdown.customizeRemark = { com.overzealous.remark.Options options -> ... }

Allows for customization of the Remark `Options` before creating a `Remark` using
a closure. This closure will be called at the time the `Remark` is first needed,
not necessarily at startup.

## Scripts ##

 * **markdown-to-html** - Converts Markdown sources to HTML
 * **html-to-markdown** - Converts HTML sources to Markdown

[grails-markdown]: http://grails.org/plugin/markdown
[Daring Fireball]: http://daringfireball.net/projects/markdown/basics
[Pegdown]: http://pegdown.org
[Remark]: http://remark.overzealous.com/manual/index.html
[Markdown Extra]: http://michelf.com/projects/php-markdown/extra/
[Multimarkdown]: http://fletcher.github.com/peg-multimarkdown/#tables

