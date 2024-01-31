package com.kh.view.plan;

import com.kh.model.vo.Plan;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

public class CommandLineTester {

  private ByteArrayInputStream in;
  private ByteArrayOutputStream out;
  private List<Plan> originals;

  public CommandLineTester() {
    out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));
  }

  public void clear() {
    System.setOut(System.out);
    System.setIn(System.in);
  }

  public void setInput(String input) {
    in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
  }

  public String getOutput() {
    return out.toString();
  }
}
