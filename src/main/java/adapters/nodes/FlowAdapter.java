package adapters.nodes;

import java.util.List;

import org.omg.sysml.lang.sysml.Classifier;
import org.omg.sysml.lang.sysml.Feature;
import org.omg.sysml.lang.sysml.FeatureReferenceExpression;
import org.omg.sysml.lang.sysml.FlowEnd;
import org.omg.sysml.lang.sysml.FlowUsage;
import org.omg.sysml.lang.sysml.ReferenceUsage;
import org.omg.sysml.lang.sysml.Type;
import org.omg.sysml.lang.sysml.Redefinition;
import org.omg.sysml.lang.sysml.Usage;

import adapters.utils.NamedElementAdapter;
import interfaces.nodes.IFlow;

/**
 * Implementação inicial do adaptador de Flow. Recebe uma FlowUsage e resolve
 * source/target/payload de forma defensiva.
 */
public class FlowAdapter extends NamedElementAdapter implements IFlow {
	private final FlowUsage flow;

	public FlowAdapter(FlowUsage flow) {
		super(flow);
		this.flow = flow;
	}

	@Override
	public String getName() {
		return getDeclaredName();
	}

	@Override
	public String getPayload() {
		if (flow == null)
			return null;

		try {
			if (flow.getOwnedFeature() != null) {
				for (Feature of : flow.getOwnedFeature()) {
					// class name and declaredName (safe)
					String ofClass = of.eClass() != null ? of.eClass().getName() : "";
					String ofDeclared = of.getDeclaredName() != null ? of.getDeclaredName()
							: (of.getName() != null ? of.getName() : null);

					// detecta payload pelo nome ou pela classe
					boolean isPayload = (ofDeclared != null && "payload".equalsIgnoreCase(ofDeclared))
							|| (ofClass != null && ofClass.toLowerCase().contains("payload"));

					if (!isPayload)
						continue;

					// tenta extrair o tipo do payload (ex: Fuel)
					try {
						var types = of.getType();
						if (types != null && !types.isEmpty() && types.get(0) != null) {
							Type t = types.get(0);
							String tname = t.getDeclaredName() != null ? t.getDeclaredName()
									: (t.getName() != null ? t.getName() : null);
							if (tname != null && !tname.isEmpty())
								return tname;
						}
					} catch (Exception ex) {

					}

					// fallback: retornar o nome da feature payload
					if (ofDeclared != null && !ofDeclared.isEmpty())
						return ofDeclared;
				}
			}
		} catch (Exception ex) {
			// swallow
		}

		return null;
	}

	@Override
	public String getSource() {
		return resolveFromRelatedOrFlowEnd(0);
	}

	@Override
	public String getTarget() {
		return resolveFromRelatedOrFlowEnd(1);
	}

	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();

	    sb.append("flow");
	    if (getName() != null && !getName().isBlank()) {
	        sb.append(" ").append(getName());
	    }

	    if (getPayload() != null && !getPayload().isBlank()) {
	        sb.append(" of ").append(getPayload());
	    }

	    String src = getSource() != null ? getSource() : "<unknown>";
	    String tgt = getTarget() != null ? getTarget() : "<unknown>";

	    sb.append(" from ").append(src).append(" to ").append(tgt);

	    return sb.toString();
	}

	private String resolveFromRelatedOrFlowEnd(int index) {
		if (flow == null)
			return null;

		// 1) tentar relatedFeature
		try {
			List<Feature> related = flow.getRelatedFeature();
			if (related != null && related.size() > index) {
				Feature rel = related.get(index);

				String relName = prettyFeatureFrom(rel); // tenta extrair nome do rel (usage ou feature)
				if (relName != null && !relName.isEmpty()) {
					if (rel instanceof Usage) {
						String featFromEnd = getFeatureNameFromFlowEnd(index);
						if (featFromEnd != null) {
							return relName + "." + featFromEnd;
						}
						return relName; // FlowEnd sem feature
					}
					return relName; // rel não for usage
				}
			}
		} catch (Exception ex) {
			// fallback se algo deu errado
		}

		// 2) procurar ReferenceUsage dentro do FlowEnd
		try {
			List<FlowEnd> ends = flow.getFlowEnd();
			if (ends != null && ends.size() > index) {
				FlowEnd fe = ends.get(index);
				if (fe != null && fe.getOwnedFeature() != null) {
					for (Feature f : fe.getOwnedFeature()) {
						if (f instanceof ReferenceUsage ru) {
							// caminho ownedRedefinition -> redefinedFeature -> owner
							try {
								if (!ru.getOwnedRedefinition().isEmpty() && ru.getOwnedRedefinition().get(0) != null) {
									var red = ru.getOwnedRedefinition().get(0);
									var redefined = red.getRedefinedFeature();
									if (redefined != null && redefined.getOwner() instanceof Usage us) {
										String ownerName = us.getDeclaredName() != null ? us.getDeclaredName()
												: us.getName();
										String featName = redefined.getDeclaredName() != null
												? redefined.getDeclaredName()
												: redefined.getName();
										if (ownerName != null && featName != null) {
											return ownerName + "." + featName;
										}
										if (featName != null)
											return featName;
									}
								}
							} catch (Exception ex) {
								// try next ReferenceUsage
							}

							// se ownedRedefinition não funcionou, tentar chainingFeature
							try {
								List<Feature> chain = ru.getChainingFeature();
								if (chain != null && !chain.isEmpty()) {
									StringBuilder sb = new StringBuilder();
									boolean first = true;
									for (Feature p : chain) {
										String part = null;
										if (p instanceof FeatureReferenceExpression fre) {
											try {
												String ef = fre.namingFeature().effectiveName();
												if (ef != null && !ef.isEmpty())
													part = ef;
											} catch (Exception e) {
												/* ignore */ }
										}
										if (part == null)
											part = p.getDeclaredName() != null ? p.getDeclaredName() : p.getName();
										if (part == null)
											part = "<no-name>";
										if (!first)
											sb.append(".");
										sb.append(part);
										first = false;
									}
									if (sb.length() > 0)
										return sb.toString();
								}
							} catch (Exception ex) {
								// ignore and continue
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			// swallow
		}

		// 3) último recurso: tentar qualquer relatedFeature que produza algo
		try {
			List<Feature> related = flow.getRelatedFeature();
			if (related != null) {
				for (Feature r : related) {
					String pf = prettyFeatureFrom(r);
					if (pf != null)
						return pf;
				}
			}
		} catch (Exception ex) {
			/* ignore */ }

		return null;
	}

	// Retorna a Feature a partir do FlowEnd
	private String getFeatureNameFromFlowEnd(int index) {
		try {
			List<FlowEnd> ends = flow.getFlowEnd();
			if (ends == null || ends.size() <= index)
				return null;
			FlowEnd fe = ends.get(index);
			if (fe == null)
				return null;
			if (fe.getOwnedFeature() == null)
				return null;

			for (Feature f : fe.getOwnedFeature()) {
				if (f instanceof ReferenceUsage ru) {
					// Tentar ownedRedefinition -> redefinedFeature (nome da feature)
					try {
						if (!ru.getOwnedRedefinition().isEmpty() && ru.getOwnedRedefinition().get(0) != null) {
							var red = ru.getOwnedRedefinition().get(0);
							var redefined = red.getRedefinedFeature();
							if (redefined != null) {
								String featName = redefined.getDeclaredName() != null ? redefined.getDeclaredName()
										: redefined.getName();
								if (featName != null && !featName.isEmpty())
									return featName;
							}
						}
					} catch (Exception ex) {

					}

					// fallback: chainingFeature
					try {
						List<Feature> chain = ru.getChainingFeature();
						if (chain != null && !chain.isEmpty()) {
							StringBuilder sb = new StringBuilder();
							boolean first = true;
							for (Feature p : chain) {
								String part = null;
								if (p instanceof FeatureReferenceExpression fre) {
									try {
										String ef = fre.namingFeature().effectiveName();
										if (ef != null && !ef.isEmpty())
											part = ef;
									} catch (Exception e) {
										/* ignore */ }
								}
								if (part == null)
									part = p.getDeclaredName() != null ? p.getDeclaredName() : p.getName();
								if (part == null)
									part = "<no-name>";
								if (!first)
									sb.append(".");
								sb.append(part);
								first = false;
							}
							if (sb.length() > 0)
								return sb.toString();
						}
					} catch (Exception ex) {
						// ignore
					}

					// fallback final: declaredName/name do ReferenceUsage em si
					String rn = ru.getDeclaredName() != null ? ru.getDeclaredName() : ru.getName();
					if (rn != null && !rn.isEmpty())
						return rn;
				}
			}
		} catch (Exception ex) {
			// swallow
		}
		return null;
	}
	
	private String prettyFeatureFrom(Feature rel) {
	    if (rel == null) return null;

	    try {
	        // 1) Usage direto
	        if (rel instanceof Usage u) {
	            String n = u.getDeclaredName() != null ? u.getDeclaredName() : u.getName();
	            if (n != null && !n.isEmpty()) return n;
	        }

	        // 2) FeatureReferenceExpression -> namingFeature().effectiveName()
	        if (rel instanceof FeatureReferenceExpression fre) {
	            try {
	                String ef = fre.namingFeature().effectiveName();
	                if (ef != null && !ef.isEmpty()) return ef;
	            } catch (Exception ex) {

	            }
	        }

	        // 3) ReferenceUsage
	        if (rel instanceof ReferenceUsage ru) {
	            // a) caminho Ed
	            try {
	                if (!ru.getOwnedRedefinition().isEmpty() && ru.getOwnedRedefinition().get(0) != null) {
	                    Redefinition red = ru.getOwnedRedefinition().get(0);
	                    Feature redefined = red.getRedefinedFeature();
	                    if (redefined != null) {
	                        if (redefined.getOwner() instanceof Usage us) {
	                            String ownerName = us.getDeclaredName() != null ? us.getDeclaredName() : us.getName();
	                            String featName = redefined.getDeclaredName() != null ? redefined.getDeclaredName() : redefined.getName();
	                            if (ownerName != null && featName != null) return ownerName + "." + featName;
	                            if (featName != null) return featName;
	                        } else {
	                            String featName = redefined.getDeclaredName() != null ? redefined.getDeclaredName() : redefined.getName();
	                            if (featName != null) return featName;
	                        }
	                    }
	                }
	            } catch (Exception ex) {
	                // ignore e tentar chainingFeature abaixo
	            }

	            // b) chainingFeature fallback
	            try {
	                List<Feature> chain = ru.getChainingFeature();
	                if (chain != null && !chain.isEmpty()) {
	                    StringBuilder sb = new StringBuilder();
	                    boolean first = true;
	                    for (Feature f : chain) {
	                        String part = null;
	                        if (f instanceof FeatureReferenceExpression fr) {
	                            try {
	                                String ef = fr.namingFeature().effectiveName();
	                                if (ef != null && !ef.isEmpty()) part = ef;
	                            } catch (Exception e) { /* ignore */ }
	                        }
	                        if (part == null) part = f.getDeclaredName() != null ? f.getDeclaredName() : f.getName();
	                        if (part == null) part = "<no-name>";
	                        if (!first) sb.append(".");
	                        sb.append(part);
	                        first = false;
	                    }
	                    if (sb.length() > 0) return sb.toString();
	                }
	            } catch (Exception ex) {
	                // ignore
	            }
	        }

	        // 4) declaredName ou getName da Feature
	        if (rel.getDeclaredName() != null && !rel.getDeclaredName().isEmpty()) return rel.getDeclaredName();
	        if (rel.getName() != null && !rel.getName().isEmpty()) return rel.getName();

	    } catch (Throwable t) {

	    }

	    return null;
	}

	@Override
	public String getDeclaredName() {
		// TODO Auto-generated method stub
		return null;
	}

	
}