package org.example.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ConfigTS {
     private int iteration;
     private int nSize;
     private int tabuSize;

     public String getConfigEAFileName() {
          return String.format("iter-%d_nSize-%d_tabuSize-%d",
                  iteration, nSize, tabuSize);
     }
}
