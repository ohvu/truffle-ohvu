package org.calipto.node.intrinsic;

import org.calipto.CaliptoContext;
import org.calipto.CaliptoLanguage;
import org.calipto.type.DataLibrary;

import com.oracle.truffle.api.dsl.CachedContext;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameUtil;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeField(name = "slot", type = FrameSlot.class)
@NodeInfo(shortName = "expand")
public abstract class ExpandNode extends IntrinsicNode {
  protected abstract FrameSlot getSlot();

  public ExpandNode() {
    // TODO Auto-generated constructor stub
  }

  @Specialization(guards = "conses.isCons(cons)", limit = "3")
  Object doDefault(
      VirtualFrame frame,
      Object cons,
      @CachedLibrary("cons") DataLibrary conses,
      @CachedContext(CaliptoLanguage.class) CaliptoContext context) {
    var value = conses.car(cons);

    var macros = frame.getObject(frame.getFrameDescriptor().findFrameSlot("(macros)"));

    if (setContains(macros, value)) {
      frame.getFrameDescriptor().findFrameSlot(value);
    }

    return FrameUtil.getObjectSafe(frame, getSlot());
  }

  @Specialization(guards = "symbols.isSymbol(symbol)", limit = "3")
  Object doDefault(Object symbol, @CachedLibrary("symbol") DataLibrary symbols) {
    return symbol;
  }
}
