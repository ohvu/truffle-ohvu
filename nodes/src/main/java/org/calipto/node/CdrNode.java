package org.calipto.node;

import org.calipto.type.DataLibrary;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(shortName = "cdr")
@NodeChild("cons")
public abstract class CdrNode extends CaliptoNode {
  @Specialization(limit = "3")
  Object doDefault(Object cons, @CachedLibrary("cons") DataLibrary conses) {
    return conses.cdr(cons);
  }
}