# Coupleteer

A java-written Shakespeare-trained couplet generator.

## Background

Poetry is a frequent feature in natural languages, either deriving from music,
writing, prayer or storytelling. It seems that the elements that define
poetry&mdash;euphony, rhythm, rhyme, formalism, symbolism, wordplay&mdash;serve
as mnemonic devices to aid in the recitation of compositions. Attention to these
features, even though not required for language generation, may help add a
natural and pleasant sounding flavor to the generated phrases.

## Installation

This program builds with [Maven](https://maven.apache.org/install.html). Once
the project is cloned, from a command line session at the root of the project,
enter:
```
mvn clean install
```

The target jar will be built to `./target/coupleteer-1.0.jar`

## Running

To run an interactive version of the generator:

```
java -jar coupleteer-1.0.jar
```

To generate a specific number of couplets:

```
java -jar coupleteer-1.0.jar [number]
```

## Description

### Corpora
Coupleteer is trained on two corpora: the CMU Pronouncing Dictionary and
WordHoard.

#### CMU Pronouncing Dictionary
[The CMU Pronouncing Dictionary](http://www.speech.cs.cmu.edu/cgi-bin/cmudict)
contains over 100 000 English words, each one broken down into an ARPA-bet
representation. The object of this corpus is to assist in computer speech,
but its data also provides an easily processed pronunciation that can be broken
down into rhythm and rhyme.

Here is an example entry:
```
BEATRICE  B IY1 AH0 T R IH0 S
```
Each vowel is marked with an accent weight, `1` for the primary stress, `2` for
the secondary, and `0` for no stress.

Coupleteer has run through the whole corpus, saving each word (and in their
variations) and storing both their rhythm and rhyme. The rhythm is stored as a
list of integers, zero through two, and the rhyme is defined as the last
accented vowel of a word and all following ARPA-bet characters to the end of
the word.

#### WordHoard
[WordHoard](http://wordhoard.northwestern.edu/) is a poetry corpus from
Northwestern University. Though designed for word and part of speech counting,
it has a plethora of metadata. It contains some Ancient Greek poetry, Chaucer,
Spenser and Shakespeare.

Coupleteer trained its grammar on the metered lines of the Shakespeare portion
of the corpus. It marks the beginning and ending of lines, and builds a bigram
model forward and backward for each line. It converts from WordHoard's 192 POS
tags into the Penn Tree Bank tags<sup>1</sup>.

Coupleteer also trains its word part of speech-to-word emissions. It omits any
word from the emissions that are not present in the CMU corpus, but this number
is surprisingly given CMU's focus on contemporary modern English.

### Generation
Coupleteer builds a couplet by selecting words based off of parts of speech and
rhythm. Lines are set at a minimum of ten syllables and a maximum of eleven,
allowing for feminine line endings and more variety in rhymes. Accented
syllables are only allowed on even numbered syllables. Particles and single
syllable function words are allowed to sit in unaccented positions even if their
vowel is marked as having weight. Words with two weighted syllables adjacent
were omitted from the vocabulary.

In the first line, the generator selects at (weighted) random a part of speech
based on the previous part of speech, and then selects a word that fits into
the rhythm at that point in the line from random among the part of speech's
words. Once the line has at least ten syllables, the line is considered
complete.

The last word in the line's rhyme pattern is then used to find another word with
the same rhyme to be used in the end of the second line. Because rhyme is
defined in part by vowel weight, the second word is guaranteed to fit in the
rhythm of the meter. The second line is the filled out like the first line, only
from back to front.

<hr />
###### Footnotes

1: In future iterations of Coupleteer, we will use the native WordHoard tags for
POS tagging.
